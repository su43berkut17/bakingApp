package com.su43berkut17.nanodegree.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_detail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_detail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECIPE_ID="recipeId";
    private static final String ARG_VIDEO_URL="videoUrl";
    private static final String ARG_THUMBNAIL="thumbnail";
    private static final String ARG_STEP_TEXT="stepText";
    private static final String ARG_CURRENT_STEP="currentStep";
    private static final String ARG_TOTAL_STEP="totalStep";

    //items
    private static int mRecipeId;
    private static String mVideoUrl;
    private static String mThumbnail;
    private static String mStepText;
    private static int mCurrentStep;
    private static int mTotalStep;

    private OnFragmentInteractionListener mListener;

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
        //return inflater.inflate(R.layout.fragment_fragment_detail, container, false);
        View detailView = inflater.inflate(R.layout.fragment_fragment_detail, container, false);

        Button buttonNext = detailView.findViewById(R.id.btn_next_step);
        Button buttonPrevious = detailView.findViewById(R.id.btn_previous_step);

        //we check which buttons to show
        if (mTotalStep > mCurrentStep) {
            //we check if we are in the last step
            if (mTotalStep == mCurrentStep + 1) {
                buttonNext.setVisibility(View.GONE);
            }
        } else {
            //we check if we are in the initial position
            if (mCurrentStep == 0) {
                buttonPrevious.setVisibility(View.GONE);
            }
        }

        //add the listeners to the buttons
        /*buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction("nextView",mCurrentStep,mTotalStep);
            }
        });*/

        buttonNext.setOnClickListener(view ->
                mListener.onFragmentInteraction("nextView", mCurrentStep, mTotalStep)
        );

        /*buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction("previousView",mCurrentStep,mTotalStep);
            }
        });*/
        buttonPrevious.setOnClickListener(view ->
                mListener.onFragmentInteraction("previousView", mCurrentStep, mTotalStep)
        );

        initiateVideoPlayer(detailView);

        return detailView;
    }

    private void initiateVideoPlayer(View detailview){
        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        PlayerView playerView;
        playerView=detailview.findViewById(R.id.recipe_player);
        playerView.setPlayer(player);

        //
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String typeOfButton, int currentStep, int totalStep) {
        if (mListener != null) {
            mListener.onFragmentInteraction(typeOfButton,currentStep,totalStep);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String typeOfButton, int currentStep, int totalStep);
    }
}
