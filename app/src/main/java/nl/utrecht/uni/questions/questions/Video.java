package nl.utrecht.uni.questions.questions;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video extends Fragment implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    VideoView videoView;
    int position;

    public static Video newInstance() {
        Video fragment = new Video();

        return fragment;
    }

    public Video() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoView != null) {
            videoView.stopPlayback();
            videoView = null;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.btn_skip_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToQuestion();
            }
        });

        videoView = (VideoView) getActivity().findViewById(R.id.video);

        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        videoView.setKeepScreenOn(true);

        MediaController mediaController = new MediaController(getActivity());
        mediaController.setMediaPlayer(videoView);

        videoView.setMediaController(mediaController);
        videoView.requestFocus();

        Uri uri = Uri.parse("android.resource://nl.utrecht.uni.questions.questions/" + R.raw.video);
        videoView.setVideoURI(uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
        position = videoView.getCurrentPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.seekTo(position);
            videoView.requestFocus();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        redirectToQuestion();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void redirectToQuestion() {
        Fragment newFragment = new Question();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
