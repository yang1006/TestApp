package yll.self.testapp.userinterface;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import yang1006.com.showalltextview.ShowAllSpan;
import yang1006.com.showalltextview.ShowAllTextView;
import yll.self.testapp.R;

/**
 * Created by yll on 17/7/21.
 */

public class ShowAllTextActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showalltext);

        ShowAllTextView tv_1 = (ShowAllTextView) findViewById(R.id.tv_1);
        ShowAllTextView tv_2 = (ShowAllTextView) findViewById(R.id.tv_2);

        tv_1.setMaxShowLines(3);
        tv_1.setMyText("Dwayne Jones was a Jamaican 16-year-old who was killed by a violent mob " +
                "in Montego Bay on the night of 21 July 2013, after he attended a dance party " +
                "dressed in women's clothing. Perceived as effeminate, Jones had been bullied " +
                "in school and rejected by his father, and had moved into a derelict house in " +
                "Montego Bay with transgender friends. When some men at the dance party discovered " +
                "that the cross-dressing Jones was not a woman, they confronted and attacked him. " +
                "He was beaten, stabbed, shot, and run over by a car. Police investigated, " +
                "but the murder remains unsolved. The death made news internationally. " +
                "While voices on social media accused Jones of provoking his killers by ");
        tv_1.setOnAllSpanClickListener(new ShowAllSpan.OnAllSpanClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowAllTextActivity.this, "点击了全文1", Toast.LENGTH_SHORT).show();
            }
        });

        tv_2.setMaxShowLines(5);
        tv_2.setMyText("水温刚刚合适，服侍我的是那个面目清秀的男孩，他把手慢慢的挪到我两腿之间，抚摸着我白皙的长腿，他小心翼翼的为我脱毛，神情紧张，生怕弄疼了我。\n" +
                "我就这样躺在白砂做的浴缸里，男孩轻轻的在我胸口，腹部均匀的涂抹浴盐，又在我的背部涂抹类似于橄榄油一样的护肤品。\n" +
                "男孩从一旁取出一些瓶瓶罐罐的香料，他把一些类似于花瓣一样的红色的颗粒撒在我的周围，并用纱布把那种名贵的香料挤出汁液淋在我身上。我的身体愈加的酥软，真的太舒服了，男孩的手法娴熟，让我如痴如醉，他不断的在我沐浴的水中添加美白和使我皮肤细腻的香料。\n" +
                "水温有些升高了。\n" +
                "男孩突然把我已经瘫软的身体翻了过来，他分开我的双腿……\n" +
                "他，他拿出了相机，居然在拍照，作为一只鸡，被偷拍是我们这一行的大忌，男孩把照片印在一本花名册上，我打赌那上面一定有很多鸡的照片，可能都是他找过的吧。\n" +
                "可是我却，我却居然有些喜欢上这个男孩了。\n" +
                "他还给我在水中沐浴的照片起了个奇怪的名字：“枸杞炖鸡汤。");
        tv_2.setOnAllSpanClickListener(new ShowAllSpan.OnAllSpanClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowAllTextActivity.this, "点击了全文2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
