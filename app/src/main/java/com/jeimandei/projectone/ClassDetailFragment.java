package com.jeimandei.projectone;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText c_id, c_start, c_end, c_ins, c_sub;
    Button update, delete;
    String id;
    ViewGroup view;

    final Calendar calendar = Calendar.getInstance();

    public ClassDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassDetailFragment newInstance(String param1, String param2) {
        ClassDetailFragment fragment = new ClassDetailFragment();
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_class_detail, container, false);

        c_id = view.findViewById(R.id.detail_id_class);
        c_start = view.findViewById(R.id.detail_class_start);
        c_end = view.findViewById(R.id.detail_class_end);
        c_ins = view.findViewById(R.id.detail_class_instructor);
        c_sub = view.findViewById(R.id.detail_class_subject);

        String a = this.getArguments().getString("id");
        Config.CLASS_ID = a;

        Log.d("Ar: ", Config.CLASS_ID);
        id = a;
        c_id.setText(a);

        getJSON();

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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String start = c_start.getText().toString().trim();
                final String end = c_end.getText().toString().trim();

                class UpdateData extends AsyncTask<Void, Void, String>{

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(getContext(), "Changing Data", "Please Wait...", false, false);
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(Config.KEY_ID_CLASS, id);
                        params.put(Config.KEY_START_CLASS, start);
                        params.put(Config.KEY_END_CLASS, end);

                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(Config.URL_UPDATE_CLASS, params);

                        return res;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                        ClassFragment classFragment = new ClassFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,classFragment);
                        fragmentTransaction.commit();
                    }
                }
                UpdateData updateData = new UpdateData();
                updateData.execute();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Class?");
                builder.setMessage("Are you sure?");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        return view;
    }

    private void updateTextStart() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        c_start.setText(dateFormat.format(calendar.getTime()));
    }

    private void updateTextEnd() {
        String date = "YYYY-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        c_end.setText(dateFormat.format(calendar.getTime()));
    }

    private void getJSON() {
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
                String result = handler.sendGetResp(Config.URL_GET_DETAIL_CLASS, id);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                displayDetailData(s);

                Log.d("CekNama", s);

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_CLASS);
            JSONObject object = jsonArray.getJSONObject(0);

            String class_start = object.getString(Config.TAG_JSON_START_CLASS);
            String class_end = object.getString(Config.TAG_JSON_END_CLASS);
            String class_ins = object.getString(Config.TAG_JSON_INSTRUCTOR_CLASS);
            String class_sub = object.getString(Config.TAG_JSON_SUBJECT_CLASS);

            c_start.setText(class_start);
            c_end.setText(class_end);
            c_ins.setText(class_ins);
            c_sub.setText(class_sub);
            Log.d("CekNama", json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deleteData() {
        class Delete extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Deleting Data", "Please Wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String res = handler.sendGetResp(Config.URL_DELETE_CLASS, id);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(), "Participant Deleted!!!", Toast.LENGTH_SHORT).show();

                ClassFragment classFragment = new ClassFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,classFragment);
                fragmentTransaction.commit();
            }
        }
        Delete delete = new Delete();
        delete.execute();
    }
}