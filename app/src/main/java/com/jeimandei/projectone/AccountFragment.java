package com.jeimandei.projectone;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText name, email, phonenumber, birthday;
    private CountryCodePicker country;
    private RadioGroup gender;
    private RadioButton male, female;
    private FloatingActionButton save;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_account, container, false);
        name = (EditText) viewGroup.findViewById(R.id.acc_name);
        save = (FloatingActionButton) viewGroup.findViewById(R.id.acc_save);

        birthday = (EditText) viewGroup.findViewById(R.id.acc_birthday)
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDialog();
            }
        });

        return viewGroup;
    }

    private void saveDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Hello, " + name.getText());
        alertDialog.setMessage("Are you sure?");
        alertDialog.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton("Cancel", null);
        alertDialog.setPositiveButton("Sure", null);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}