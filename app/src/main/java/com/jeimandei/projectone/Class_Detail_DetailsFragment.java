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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Class_Detail_DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Class_Detail_DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String JSON_STRING;
    private ViewGroup viewGroup;
    TextView sub, ins;
    String id, Ssub, Sins;
    private ListView lv_detailClassdetail;

    public Class_Detail_DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Class_Detail_DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Class_Detail_DetailsFragment newInstance(String param1, String param2) {
        Class_Detail_DetailsFragment fragment = new Class_Detail_DetailsFragment();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_class__detail__details, container, false);
        String a = this.getArguments().getString("id");
        Config.CLASS_ID = a;

        Log.d("Ar: ", Config.CLASSDETAIL_CLASSID_ID);
        id = a;

        lv_detailClassdetail = (ListView) viewGroup.findViewById(R.id.lv_classdetail_details);
        sub = (TextView) viewGroup.findViewById(R.id.cdd_subject_name);
        ins = (TextView) viewGroup.findViewById(R.id.cdd_instructor_name);

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
                String result = handler.sendGetResp(Config.URL_GET_ALL_CLASSDETAIL_BY_CLASSID, id);
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
                //String cdtotalparticipant = object.getString(Config.TAG_JSON_TOTALPARTICIPANT_CLASSDETAIL);
                //String cdclassstart = object.getString(Config.TAG_JSON_CLASSSTART_CLASSDETAIL);
                String cdclassid= object.getString(Config.TAG_JSON_CLASSID_CLASSDETAIL);
                String cdparticipantname= object.getString(Config.TAG_JSON_PARTICIPANTNAME_CLASSDETAIL);

                HashMap<String, String> cd = new HashMap<>();
                cd.put(Config.TAG_JSON_ID_CLASSDETAIL, id);
                cd.put(Config.TAG_JSON_SUBJECTNAME_CLASSDETAIL, cdsubjectname);
                cd.put(Config.TAG_JSON_INSTRUCTORNAME_CLASSDETAIL, cdinstructorname);
                //cd.put(Config.TAG_JSON_TOTALPARTICIPANT_CLASSDETAIL, cdtotalparticipant);
                //cd.put(Config.TAG_JSON_CLASSSTART_CLASSDETAIL, cdclassstart);
                cd.put(Config.TAG_JSON_CLASSID_CLASSDETAIL, cdclassid);
                cd.put(Config.TAG_JSON_PARTICIPANTNAME_CLASSDETAIL, cdparticipantname);
                Sins = cdinstructorname;
                Ssub = cdsubjectname;

                arrayList.add(cd);
                //Log.d("DataArr: ", String.valueOf(cd));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                viewGroup.getContext(), arrayList, R.layout.lv_classdetail_details,
                new String[] {Config.TAG_JSON_ID_CLASSDETAIL, Config.TAG_JSON_PARTICIPANTNAME_CLASSDETAIL},
                new int[] {R.id.lv_classdetailclass_id, R.id.lv_cdd_participantname}
        );
        ins.setText(Sins);
        sub.setText(Ssub);
        Log.d("DataArray: ", String.valueOf(adapter));
        lv_detailClassdetail.setAdapter(adapter);

    }

}