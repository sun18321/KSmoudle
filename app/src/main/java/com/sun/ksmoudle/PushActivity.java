package com.sun.ksmoudle;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.StreamerConstants;

public class PushActivity extends AppCompatActivity {

    private GLSurfaceView mCameraPreview;
    private KSYStreamer mStreamer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push);

        init();
        startKSYStreamer();
        streamListener();
        //开始推流
        mStreamer.startStream();
    }

    private void streamListener() {
        // 设置Info回调，可以收到相关通知信息
        mStreamer.setOnInfoListener(new KSYStreamer.OnInfoListener() {
            @Override
            public void onInfo(int what, int msg1, int msg2) {
                // ...
            }
        });
// 设置错误回调，收到该回调后，一般是发生了严重错误，比如网络断开等，
// SDK内部会停止推流，APP可以在这里根据回调类型及需求添加重试逻辑。
        mStreamer.setOnErrorListener(new KSYStreamer.OnErrorListener() {
            @Override
            public void onError(int what, int msg1, int msg2) {
                // ...
            }
        });
    }

    private void startKSYStreamer() {
        // 创建KSYStreamer实例
        mStreamer = new KSYStreamer(this);
// 设置预览View
        mStreamer.setDisplayPreview(mCameraPreview);
// 设置推流url（需要向相关人员申请，测试地址并不稳定！）
        mStreamer.setUrl("rtmp://test.uplive.ksyun.com/live/{streamName}");
// 设置预览分辨率, 当一边为0时，SDK会根据另一边及实际预览View的尺寸进行计算
        mStreamer.setPreviewResolution(480, 0);
// 设置推流分辨率，可以不同于预览分辨率
        mStreamer.setTargetResolution(480, 0);
// 设置预览帧率
        mStreamer.setPreviewFps(15);
// 设置推流帧率，当预览帧率大于推流帧率时，编码模块会自动丢帧以适应设定的推流帧率
        mStreamer.setTargetFps(15);
// 设置视频码率，分别为初始平均码率、最高平均码率、最低平均码率，单位为kbps，另有setVideoBitrate接口，单位为bps
        mStreamer.setVideoKBitrate(600, 800, 400);
// 设置音频采样率
        mStreamer.setAudioSampleRate(44100);
// 设置音频码率，单位为kbps，另有setAudioBitrate接口，单位为bps
        mStreamer.setAudioKBitrate(48);
/**
 * 设置编码模式(软编、硬编):
 * StreamerConstants.ENCODE_METHOD_SOFTWARE
 * StreamerConstants.ENCODE_METHOD_HARDWARE
 */
        mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
// 设置屏幕的旋转角度，支持 0, 90, 180, 270
        mStreamer.setRotateDegrees(0);
// 开启推流统计功能
        mStreamer.setEnableStreamStatModule(true);
    }

    private void init() {
        mCameraPreview = (GLSurfaceView)findViewById(R.id.camera_preview);
    }
}
