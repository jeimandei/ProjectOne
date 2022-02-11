package com.jeimandei.projectone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Class_DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Class_DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView lv_detailClass;

    public Class_DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Class_DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Class_DetailFragment newInstance(String param1, String param2) {
        Class_DetailFragment fragment = new Class_DetailFragment();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_class__detail, container, false);

        lv_detailClass = (ListView) viewGroup.findViewById(R.id.lv_classdetail);

        getJSON();

        return viewGroup;
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
                String result = handler.sendGetResp(Config.URL_GET_ALL_CLASSDETAIL);
                Log.d("GetData", result);
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
                }, 3000);

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                displayAllParticipant();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllParticipant() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_CLASSDETAIL);
            Log.d("DataArr: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(Config.TAG_JSON_ID_CLASSDETAIL);
                String cdsubjectname = object.getString(Config.TAG_JSON_SUBJECTNAME_CLASSDETAIL);
                String cdinstructorname = object.getString(Config.TAG_JSON_INSTRUCTORNAME_CLASSDETAIL);
                String cdtotalparticipant = object.getString(Config.TAG_JSON_TOTALPARTICIPANT_CLASSDETAIL);
                String cdclassstart = object.getString(Config.TAG_JSON_CLASSSTART_CLASSDETAIL);
                String cdclassid= object.getString(Config.TAG_JSON_CLASSID_CLASSDETAIL);

                HashMap<String, String> cd = new HashMap<>();
                cd.put(Config.TAG_JSON_ID_CLASSDETAIL, id);
                cd.put(Config.TAG_JSON_SUBJECTNAME_CLASSDETAIL, cdsubjectname);
                cd.put(Config.TAG_JSON_INSTRUCTORNAME_CLASSDETAIL, cdinstructorname);
                cd.put(Config.TAG_JSON_TOTALPARTICIPANT_CLASSDETAIL, cdtotalparticipant);
                cd.put(Config.TAG_JSON_CLASSSTART_CLASSDETAIL, cdclassstart);
                cd.put(Config.TAG_JSON_CLASSID_CLASSDETAIL, cdclassid);

                arrayList.add(cd);
                //Log.d("DataArr: ", String.valueOf(cd));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                viewGroup.getContext(), arrayList, R.layout.lv_classdetail,
                new String[] {Config.TAG_JSON_ID_CLASSDETAIL, Config.TAG_JSON_SUBJECTNAME_CLASSDETAIL, Config.TAG_JSON_INSTRUCTORNAME_CLASSDETAIL, Config.TAG_JSON_TOTALPARTICIPANT_CLASSDETAIL, Config.TAG_JSON_CLASSSTART_CLASSDETAIL},
                new int[] {R.id.lv_classdetail_id, R.id.lv_cd_subject_name, R.id.lv_cd_instructor_name, R.id.lv_cd_total_participant, R.id.lv_cd_classstart}
        );
        Log.d("DataArray: ", String.valueOf(adapter));
        lv_detailClass.setAdapter(adapter);

        lv_detailClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Class_Detail_DetailsFragment class_detail_detailsFragment = new Class_Detail_DetailsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,class_detail_detailsFragment);


                HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
                String classid = map.get(Config.TAG_JSON_CLASSID_CLASSDETAIL).toString();
                Bundle args = new Bundle();
                args.putString("id", classid);
                class_detail_detailsFragment.setArguments(args);



                Log.d("Par: ", String.valueOf(args));
                fragmentTransaction.commit();
            }
        });



    }
}