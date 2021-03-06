package org.wordcamp.wcdetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.wordcamp.R;
import org.wordcamp.WordCampDetailActivity;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.utils.ImageUtils;

/**
 * Created by aagam on 26/1/15.
 */
public class WordCampOverview extends Fragment {
    private WordCampDB wc;
    private TextView location;
    private TextView about;
    private ImageView wcFeaturedImage;
    private WordCampOverviewListener listener;
    private SwipeRefreshLayout refreshLayout;
    private View aboutView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wcdetails_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wc = ((WordCampDetailActivity) getActivity()).wcdb;
        View v = getView();
        View v1 = v.findViewById(R.id.wc_image_container);

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"),
                Color.parseColor("#FF4081"), Color.parseColor("#9C27B0"));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefreshOverview();
            }
        });

        wcFeaturedImage = (ImageView) v.findViewById(R.id.featuredImage);
        if (wc.featureImageUrl != null && !wc.featureImageUrl.equals("")) {
//            Picasso.with(getActivity()).load("http://central.wordcamp.org/files/2014/12/Norway.png").placeholder(R.drawable.wcparis).into(wcFeaturedImage);
        }
        location = (TextView) v.findViewById(R.id.wc_location);
        setLocationText();

        aboutView = v.findViewById(R.id.wc_about_header);
        about = (TextView) v.findViewById(R.id.wc_about);
        setAboutText();

        v1.getLayoutParams().height = ImageUtils.getAspectRatio(getActivity());
        View maps = v.findViewById(R.id.maps_cotainer);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });
        ViewCompat.setElevation(v.findViewById(R.id.centerLayoutDetail), getResources().getDimension(R.dimen.list_elevation));
    }

    private void openMaps() {
        String map = "http://maps.google.com/maps?q="
                + location.getText().toString().replaceAll("\n|\r", ",").replaceAll(" ", "+");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(intent);
    }

    private void setLocationText() {
        location.setText(wc.getVenue() + "\n" + wc.getAddress());
    }

    private void setAboutText() {
        if (wc.getAbout().isEmpty())
            aboutView.setVisibility(View.GONE);
        else {
            aboutView.setVisibility(View.VISIBLE);
            about.setText(Html.fromHtml(wc.getAbout()));
        }
    }

    private void startRefreshOverview() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.refreshOverview();
            }
        });
    }

    public void stopRefreshOverview() {
        refreshLayout.setRefreshing(false);
    }

    public void updateData(WordCampDB wcd) {
        wc = wcd;
        setLocationText();
        setAboutText();
        stopRefreshOverview();
    }

    public interface WordCampOverviewListener {
        void refreshOverview();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (WordCampDetailActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WordCampOverviewListener");
        }
    }
}