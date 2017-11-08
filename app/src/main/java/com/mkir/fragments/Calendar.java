package com.mkir.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mkir.Constants;
import com.mkir.R;
import com.mkir.ServerRequest;
import com.mkir.ServerResponse;
import com.mkir.adapters.UpComing;
import com.mkir.datastreams.UpComingList;
import com.mkir.interfaces.LoginInterface;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar extends Fragment {

    MaterialCalendarView calendarView;
    int year, month, day, x;
    SharedPreferences pref;
    RecyclerView calendarrv;
    TextView month_view, week_view;
    String datepick, unique_id;
    java.util.Calendar calendar1;
    LinearLayout calendarlayout, calendarheader;
    UpComing adapter_ul;
    FloatingActionButton add_event;

    public Calendar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View calendar = inflater.inflate(R.layout.fragment_calendar, container, false);
        pref=getActivity().getPreferences(0);
        x=0;
        unique_id = pref.getString(Constants.UNIQUE_ID,"");
        calendarheader = (LinearLayout) calendar.findViewById(R.id.calendar_header);
        month_view = (TextView) calendar.findViewById(R.id.month_view);
        week_view= (TextView) calendar.findViewById(R.id.week_view);
        add_event = (FloatingActionButton) calendar.findViewById(R.id.add_event);

        calendarView = (MaterialCalendarView) calendar.findViewById(R.id.calendarview);
        calendar1 = java.util.Calendar.getInstance();
        calendarView.setDateSelected(calendar1, true);

        year = calendarView.getSelectedDate().getYear();
        month = calendarView.getSelectedDate().getMonth()+1;
        day = calendarView.getSelectedDate().getDay();
        datepick = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);

        calendarheader.setVisibility(View.GONE);
        week_view.setTextColor(getResources().getColor(R.color.colorPrimaryDark, getActivity().getTheme()));
        month_view.setTextColor(getResources().getColor(R.color.colorGrey, getActivity().getTheme()));
        calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        week_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarheader.setVisibility(View.GONE);
                month_view.setTextColor(getResources().getColor(R.color.colorGrey, getActivity().getTheme()));
                week_view.setTextColor(getResources().getColor(R.color.colorPrimaryDark, getActivity().getTheme()));
                calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
            }
        });

        month_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarheader.setVisibility(View.VISIBLE);
                month_view.setTextColor(getResources().getColor(R.color.colorPrimaryDark, getActivity().getTheme()));
                week_view.setTextColor(getResources().getColor(R.color.colorGrey, getActivity().getTheme()));
                calendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
            }
        });

        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEvent addEvent = new AddEvent();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, addEvent, addEvent.getTag())
                        .addToBackStack("1")
                        .commit();
            }
        });

        //add_event.setVisibility(View.GONE);

        calendarrv = (RecyclerView) calendar.findViewById(R.id.calendar_rv);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext());

        calendarrv.setLayoutManager(layoutManager1);
        loadJSON(unique_id,datepick);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                year = calendarView.getSelectedDate().getYear();
                month = calendarView.getSelectedDate().getMonth()+1;
                day = calendarView.getSelectedDate().getDay();
                datepick = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
                loadJSON(unique_id,datepick);


                //Toast.makeText(getContext(), String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day), LENGTH_LONG).show();
            }
        });

        //calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        calendarrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState==RecyclerView.VERTICAL)
                    if(x==0) {
                        x = 1;
                        calendarheader.setVisibility(View.GONE);
                        calendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
                    }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        return calendar;
    }

    private void loadJSON(String unique_id, String date){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginInterface loginInterface = retrofit.create(LoginInterface.class);

        UpComingList upComingList = new UpComingList();
        upComingList.setSzemely_id(unique_id);
        upComingList.setDate(date);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CALENDAR_LIST);
        request.setUpComingList(upComingList);
        Call<ServerResponse> response = loginInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                ArrayList<UpComingList> upcl = new ArrayList<>(Arrays.asList(resp.getUpComingList()));
                adapter_ul=new UpComing(upcl);
                calendarrv.setAdapter(adapter_ul);


                //Snackbar.make(getView(), data, LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Snackbar.make(getView(), t.getLocalizedMessage(), LENGTH_LONG).show();
            }
        });
    }


}
