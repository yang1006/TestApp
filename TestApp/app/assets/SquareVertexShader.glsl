
varying vec4 vColor;
attribute vec4 vPosition;
attribute vec4 aColor;

void main() {
    gl_Position = vPosition;
    vColor = aColor;
}
