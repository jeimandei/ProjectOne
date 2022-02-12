package com.jeimandei.projectone;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewGroup viewGroup;
    String JSON_STRING, start, end;
    EditText c_start, c_end;
    ListView lvinssearch;
    ArrayAdapter<String> adapter;
    Button search;

    final Calendar calendar = Calendar.getInstance();


    public SearchDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchDateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDateFragment newInstance(String param1, String param2) {
        SearchDateFragment fragment = new SearchDateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search_date, container, false);
        c_start = viewGroup.findViewById(R.id.datestartSearch);
        c_end = viewGroup.findViewById(R.id.dateendSearch);
        search = viewGroup.findViewById(R.id.search_date_button);

        lvinssearch = viewGroup.findViewById(R.id.lv_datesearch);

        DatePickerDialog.OnDateSetListener date_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateTextStart();
            }
        };
        DatePickerDialog.OnDateSetListener date_end = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateTextEnd();
            }
        };

        c_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date_start, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        c_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date_end, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSONDate();


            }
        });


        return viewGroup;
    }

    private void getJSONDate() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getContext(), "Getting Data", "Please wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetRespDate(Config.URL_SHOW_DATE, start, end);
                Log.d("res:", result);
                Log.d("start:", start);
                Log.d("end:", end);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1500);

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                JSONObject jsonObject = null;
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_DATE);
                    Log.d("ass", String.valueOf(jsonArray));
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String subject = object.getString(Config.TAG_JSON_SUBJECTNAME_DATE);
                        String ins = object.getString(Config.TAG_JSON_INSTRUCTORNAME_DATE);
                        String start = object.getString(Config.TAG_JSON_CLASSSTART_DATE);
                        String end = object.getString(Config.TAG_JSON_CLASSEND_DATE);

                        HashMap<String, String> classes = new HashMap<>();
                        classes.put(Config.TAG_JSON_SUBJECTNAME_DATE, subject);
                        classes.put(Config.TAG_JSON_INSTRUCTORNAME_DATE, ins);
                        classes.put(Config.TAG_JSON_CLASSSTART_DATE, start);
                        classes.put(Config.TAG_JSON_CLASSEND_DATE, end);

                        arrayList.add(classes);
                        Log.d("DataArr: ", String.valueOf(classes));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ListAdapter adapter = new SimpleAdapter(
                        viewGroup.getContext(), arrayList, R.layout.lv_search_date,
                        new String[] {Config.TAG_JSON_SUBJECTNAME_DATE, Config.TAG_JSON_INSTRUCTORNAME_DATE, Config.TAG_JSON_CLASSSTART_DATE, Config.TAG_JSON_CLASSEND_DATE},
                        new int[] {R.id.lv_datesearch_subject, R.id.lv_datesearch_instructor, R.id.lv_datesearch_start, R.id.lv_datesearch_end}
                );

                lvinssearch.setAdapter(adapter);
                Log.d("spin", String.valueOf(arrayList));

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    private void updateTextStart() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        c_start.setText(dateFormat.format(calendar.getTime()));
        start = dateFormat.format(calendar.getTime());
    }

    private void updateTextEnd() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        c_end.setText(dateFormat.format(calendar.getTime()));
        end = dateFormat.format(calendar.getTime());
    }

    private void clearText() {
        c_start.setText("");
        c_end.setText("");
        c_start.requestFocus();
    }
}