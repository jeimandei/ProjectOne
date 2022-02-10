package com.jeimandei.projectone;

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
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstructorDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructorDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText i_id, i_name, i_email, i_phone;
    Button update, delete;
    String id;
    ViewGroup view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InstructorDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InstructorDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InstructorDetailFragment newInstance(String param1, String param2) {
        InstructorDetailFragment fragment = new InstructorDetailFragment();
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_instructor_detail, container, false);

        i_id = view.findViewById(R.id.detail_id_instructor);
        i_name = view.findViewById(R.id.detail_name_instructor);
        i_email = view.findViewById(R.id.detail_email_instructor);
        i_phone = view.findViewById(R.id.detail_phone_instructor);
        update = view.findViewById(R.id.update_instructor);
        delete = view.findViewById(R.id.delete_instructor);

        String a = this.getArguments().getString("id");
        Config.INSTRUCTOR_ID = a;

        Log.d("Ar: ", Config.INSTRUCTOR_ID);
        id = a;
        i_id.setText(a);

        getJSON();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = i_name.getText().toString().trim();
                final String email = i_email.getText().toString().trim();
                final String phone = i_phone.getText().toString().trim();

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
                        params.put(Config.KEY_ID_INSTRUCTOR, id);
                        params.put(Config.KEY_NAME_INSTRUCTOR, name);
                        params.put(Config.KEY_EMAIL_INSTRUCTOR, email);
                        params.put(Config.KEY_PHONE_INSTRUCTOR, phone);
                        Log.d("in", name);
                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(Config.URL_UPDATE_INSTRUCTOR, params);

                        return res;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                        Log.d("aa:", s);

                        InstructorFragment instructorFragment = new InstructorFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,instructorFragment);
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
                builder.setTitle("Delete Subject?");
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
                String result = handler.sendGetResp(Config.URL_GET_DETAIL_INSTRUCTOR, id);
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
            JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_INSTRUCTOR);
            JSONObject object = jsonArray.getJSONObject(0);

            String name = object.getString(Config.TAG_JSON_NAME_INSTRUCTOR);
            String email = object.getString(Config.TAG_JSON_EMAIL_INSTRUCTOR);
            String phone = object.getString(Config.TAG_JSON_PHONE_INSTRUCTOR);

            i_name.setText(name);
            i_email.setText(email);
            i_phone.setText(phone);

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
                String res = handler.sendGetResp(Config.URL_DELETE_INSTRUCTOR, id);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(), "Subject Deleted!!!", Toast.LENGTH_SHORT).show();

                InstructorFragment instructorFragment = new InstructorFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,instructorFragment);
                fragmentTransaction.commit();
            }
        }
        Delete delete = new Delete();
        delete.execute();
    }
}