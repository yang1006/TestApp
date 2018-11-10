package yll.self.testapp.video;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaCas;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;

import yll.self.testapp.utils.UtilsManager;

public class VideoThread extends Thread {

    private Surface surface;
    private String filePath;
    private final String VIDEO_MIME = "video/";
    private final long TIMEOUT_US = 10000;

    public void init(Surface surface, String filePath){
        this.filePath = filePath;
        this.surface = surface;
    }

    @Override
    public void run() {
        MediaExtractor videoExtractor = new MediaExtractor();
        MediaCodec videoCodec = null;
        try {
            videoExtractor.setDataSource(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int videoTrackIndex = getMediaTrackIndex(videoExtractor, VIDEO_MIME);
        if (videoTrackIndex < 0){
            UtilsManager.log("Video track 小于0");
            return;
        }
        MediaFormat mediaFormat = videoExtractor.getTrackFormat(videoTrackIndex);
        int width = mediaFormat.getInteger(MediaFormat.KEY_WIDTH);
        int height = mediaFormat.getInteger(MediaFormat.KEY_HEIGHT);
        float time = mediaFormat.getLong(MediaFormat.KEY_DURATION);
        videoExtractor.selectTrack(videoTrackIndex);

        try {
            videoCodec = MediaCodec.createDecoderByType(mediaFormat.getString(MediaFormat.KEY_MIME));
            videoCodec.configure(mediaFormat, surface , null, 0);
        }catch (Exception e){}

        if (videoCodec == null){
            UtilsManager.log("videoCodec is null");
            return;
        }

        videoCodec.start();

        MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
        ByteBuffer[] inputBuffers = videoCodec.getInputBuffers();
        boolean isVideoEOS = false;
        long startMs = System.currentTimeMillis();

        while (!Thread.interrupted()){
            if (!isVideoEOS){
                isVideoEOS = putBufferToCoder(videoExtractor, videoCodec, inputBuffers);
            }
            int outBufferIndex = 0/*videoCodec.dequeueInputBuffer(videoBufferInfo, TIMEOUT_US)*/;
            switch (outBufferIndex){
                case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                    UtilsManager.log("format changed");
                    break;
                case MediaCodec.INFO_TRY_AGAIN_LATER:
                    UtilsManager.log("超时");
                    break;
                case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                    UtilsManager.log("output buffers changed");
                    break;
                default:
                    //直接渲染到Surface时使用不到outputBuffer
                    //ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                    //延时操作
                    //如果缓冲区里的可展示时间>当前视频播放的进度，就休眠一下
                    sleepRender(videoBufferInfo, startMs);
                    videoCodec.releaseOutputBuffer(outBufferIndex, true);
                    break;
            }
            if ((videoBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0){
                UtilsManager.log("buffer stream end");
                break;
            }
        } //end while

        videoCodec.stop();
        videoCodec.release();
        videoExtractor.release();
    }


    private void sleepRender(MediaCodec.BufferInfo bufferInfo, long startMs){
        while (bufferInfo.presentationTimeUs / 1000 > System.currentTimeMillis() - startMs){
            try {
                Thread.sleep(10);
            }catch (Exception e){

            }
        }
    }


    //获取轨道
    private int getMediaTrackIndex(MediaExtractor extractor, String mediaType){
        int trackIndex = -1;
        for (int i = 0; i < extractor.getTrackCount(); i++){
            MediaFormat mediaFormat = extractor.getTrackFormat(i);
            String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
            if (mime.startsWith(mediaType)){
                trackIndex = i;
                break;
            }
        }
        return trackIndex;
    }

    //将缓冲区传递到解码器
    private boolean putBufferToCoder(MediaExtractor extractor, MediaCodec decoder, ByteBuffer[] inputBuffers){
        boolean isMediaEOS = false;
        int inputBufferIndex = decoder.dequeueInputBuffer(TIMEOUT_US);
        if (inputBufferIndex >= 0){
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            int sampleSize = extractor.readSampleData(inputBuffer, 0);
            if (sampleSize < 0){
                decoder.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                isMediaEOS = true;
                UtilsManager.log("mediao eos");
            }else {
                decoder.queueInputBuffer(inputBufferIndex, 0, sampleSize, extractor.getSampleTime(), 0);
                extractor.advance();
            }
        }
        return isMediaEOS;
    }

}
