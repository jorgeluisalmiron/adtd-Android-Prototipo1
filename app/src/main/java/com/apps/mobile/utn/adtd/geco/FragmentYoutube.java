package com.apps.mobile.utn.adtd.geco;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.mobile.utn.adtd.geco.Adapter.YoutubeRecyclerViewAdapter;
import com.apps.mobile.utn.adtd.geco.Decoration.DividerItemDecoration;
import com.apps.mobile.utn.adtd.geco.Model.Profile;


public class FragmentYoutube extends Fragment {
    private OnFragmentInteractionListener mListener;

    private YoutubeRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;



    public FragmentYoutube() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // String TOKEN = null;
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewYoutube);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(rootView.getContext(), R.drawable.divider));

     /*   Bundle bundle = this.getArguments();
        if (bundle != null) {
             TOKEN = bundle.getString("TOKEN","");
        }*/
        adapter = new YoutubeRecyclerViewAdapter(rootView.getContext(), Profile.getInstance().getTOKEN());
        adapter.generateYoutubeList(rootView.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        openVideo(position);

                    }
                })
        );

        return rootView;
    }

    public void openVideo(int position)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(adapter.getItem(position).getUrl().toString())));
        Log.i("Video", "Video Playing....");

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
