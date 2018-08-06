package com.su43berkut17.nanodegree.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.su43berkut17.nanodegree.bakingapp.data.StepMenuContainer;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnStepDetailClick} interface
 * to handle interaction events.
 * Use the {@link fragment_detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_detail extends Fragment {
    //item names
    private static final String ARG_RECIPE_ID="recipeId";
    private static final String ARG_VIDEO_URL="videoUrl";
    private static final String ARG_THUMBNAIL="thumbnail";
    private static final String ARG_STEP_TEXT="stepText";
    private static final String ARG_CURRENT_STEP="currentStep";
    private static final String ARG_TOTAL_STEP="totalStep";

    //type of button
    public static final String BTN_PREVIOUS="previous_button";
    public static final String BTN_NEXT="next_button";

    //items
    private static int mRecipeId;
    private static String mVideoUrl;
    private static String mThumbnail;
    private static String mStepText;
    private static int mCurrentStep;
    private static int mTotalStep;

    private OnStepDetailClick mListener;

    //exoplayer
    SimpleExoPlayer player;

    public fragment_detail() {
        // Required empty public constructor
    }

    public static fragment_detail newInstance(int recId, String recVideo, String recThumb, String recStep, int recCurrentStep, int recTotalStep) {
        fragment_detail fragment = new fragment_detail();
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recId);
        args.putString(ARG_VIDEO_URL, recVideo);
        args.putString(ARG_THUMBNAIL, recThumb);
        args.putString(ARG_STEP_TEXT, recStep);
        args.putInt(ARG_CURRENT_STEP, recCurrentStep);
        args.putInt(ARG_TOTAL_STEP, recTotalStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeId=getArguments().getInt(ARG_RECIPE_ID);
            mVideoUrl=getArguments().getString(ARG_VIDEO_URL);
            mThumbnail=getArguments().getString(ARG_THUMBNAIL);
            mStepText=getArguments().getString(ARG_STEP_TEXT);
            mCurrentStep=getArguments().getInt(ARG_CURRENT_STEP);
            mTotalStep=getArguments().getInt(ARG_TOTAL_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailView = inflater.inflate(R.layout.fragment_fragment_detail, container, false);

        //populate the ui
        //we check if it is video only mode
        if (detailView.findViewById(R.id.btn_next_step)!=null) {
            TextView textDescription = detailView.findViewById(R.id.tv_steps);
            textDescription.setText(mStepText);

            Button buttonNext = detailView.findViewById(R.id.btn_next_step);
            Button buttonPrevious = detailView.findViewById(R.id.btn_previous_step);

            //we check which buttons to show
            //we check if we are in the last step
            if (mTotalStep == mCurrentStep + 1) {
                buttonNext.setVisibility(View.GONE);
            }
            //we check if we are in the initial position
            if (mCurrentStep == 0 || mCurrentStep == 1) {
                buttonPrevious.setVisibility(View.GONE);
            }

            buttonNext.setOnClickListener(view ->
                    mListener.OnStepDetail(BTN_NEXT, mCurrentStep, mTotalStep)
            );

            buttonPrevious.setOnClickListener(view ->
                    mListener.OnStepDetail(BTN_PREVIOUS, mCurrentStep, mTotalStep)
            );
        }

        initiateVideoPlayer(detailView);

        return detailView;
    }

    private void initiateVideoPlayer(View detailview){
        // 1. Create a default TrackSelector
        Uri videoToPlay=Uri.parse(mVideoUrl);

        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = null;
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        //SimpleExoPlayer player =
                //ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        PlayerView playerView;
        playerView=detailview.findViewById(R.id.recipe_player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        playerView.setPlayer(player);

        Uri uri = Uri.parse(mVideoUrl);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("detailFragment")).createMediaSource(uri);

        player.prepare(mediaSource,true,false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnStepDetailClick) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        player.release();
    }

    public interface OnStepDetailClick {
        void OnStepDetail(String typeOfButton, int currentStep, int totalStep);
    }
}
