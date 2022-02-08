package com.jeimandei.projectone;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

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

    private CountryCodePicker phnum;
    private RadioGroup gender;
    private RadioButton chk_gender;
    private FloatingActionButton save;
    private CircleImageView image;
    final Calendar calendar = Calendar.getInstance();


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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri selectedImageUri = data.getData();
        if (null != selectedImageUri) {
            String path = selectedImageUri.getPath();
            image.setImageURI(selectedImageUri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_account, container, false);
        image = viewGroup.findViewById(R.id.image);
        name = viewGroup.findViewById(R.id.acc_name);
        email = viewGroup.findViewById(R.id.acc_email);
        phnum = viewGroup.findViewById(R.id.acc_phCountry);
        phonenumber = viewGroup.findViewById(R.id.acc_phone);
        phnum.registerCarrierNumberEditText(phonenumber);
        gender = viewGroup.findViewById(R.id.acc_gender);
        birthday = viewGroup.findViewById(R.id.acc_birthday);
        save = viewGroup.findViewById(R.id.acc_save);




        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
            }
        });



    
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                updateText();
            }
        };
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(viewGroup.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = gender.getCheckedRadioButtonId();
                chk_gender = viewGroup.findViewById(selectedId);

                saveDialog();
            }
        });

        return viewGroup;
    }

    private void updateText() {
        String date = "dd/MM/YYYY";
        SimpleDateFormat dateFormat = new SimpleDateFormat(date, Locale.ENGLISH);
        birthday.setText(dateFormat.format(calendar.getTime()));
    }

    private void saveDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Hello, " + name.getText());
        alertDialog.setMessage("Here's your personal Information, \n\nEmail: \t\t\t" + email.getText() + "\n\nDOB: \t\t\t\t" + birthday.getText() + "\n\nPhone: \t\t" + phnum.getFormattedFullNumber() + "\n\nGender: \t" + chk_gender.getText());
        alertDialog.setIcon(getResources().getDrawable(R.drawable.ic_baseline_emoji_people_24));
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton("Cancel", null);
        alertDialog.setPositiveButton("Sure", null);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}