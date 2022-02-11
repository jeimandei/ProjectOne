package com.jeimandei.projectone;

public class Config {
    //Participant
    public static final String URL_GET_ALL_PARTICIPANT = "http://192.168.30.26/project/participant/alldata.php";
    public static final String URL_GET_DETAIL_PARTICIPANT = "http://192.168.30.26/project/participant/detail.php?participant_id=";
    public static final String URL_ADD_PARTICIPANT = "http://192.168.30.26/project/participant/add.php";
    public static final String URL_UPDATE_PARTICIPANT = "http://192.168.30.26/project/participant/update.php?participant_id=";
    public static final String URL_DELETE_PARTICIPANT = "http://192.168.30.26/project/participant/delete.php?participant_id=";


    public static final String KEY_ID_PARTICIPANT = "participantid";
    public static final String KEY_NAME_PARTICIPANT = "participantname";
    public static final String KEY_EMAIL_PARTICIPANT = "participantemail";
    public static final String KEY_PHONE_PARTICIPANT = "participantphone";
    public static final String KEY_COMPANY_PARTICIPANT = "participantcompany";

    public static final String TAG_JSON_ARRAY_PARTICIPANT = "participantresult";
    public static final String TAG_JSON_ID_PARTICIPANT = "participantid";
    public static final String TAG_JSON_NAME_PARTICIPANT = "participantname";
    public static final String TAG_JSON_EMAIL_PARTICIPANT = "participantemail";
    public static final String TAG_JSON_PHONE_PARTICIPANT = "participantphone";
    public static final String TAG_JSON_COMPANY_PARTICIPANT = "participantcompany";

    public static String PARTICIPANT_ID = "participant_id";

    //Instructor
    public static final String URL_GET_ALL_INSTRUCTOR = "http://192.168.30.26/project/instructor/alldata.php";
    public static final String URL_GET_DETAIL_INSTRUCTOR = "http://192.168.30.26/project/instructor/detail.php?instructor_id=";
    public static final String URL_ADD_INSTRUCTOR = "http://192.168.30.26/project/instructor/add.php";
    public static final String URL_UPDATE_INSTRUCTOR = "http://192.168.30.26/project/instructor/update.php?instructor_id=";
    public static final String URL_DELETE_INSTRUCTOR = "http://192.168.30.26/project/instructor/delete.php?instructor_id=";


    public static final String KEY_ID_INSTRUCTOR = "instructorid";
    public static final String KEY_NAME_INSTRUCTOR = "instructorname";
    public static final String KEY_EMAIL_INSTRUCTOR = "instructoremail";
    public static final String KEY_PHONE_INSTRUCTOR = "instructorphone";

    public static final String TAG_JSON_ARRAY_INSTRUCTOR = "instructorresult";
    public static final String TAG_JSON_ID_INSTRUCTOR = "instructorid";
    public static final String TAG_JSON_NAME_INSTRUCTOR = "instructorname";
    public static final String TAG_JSON_EMAIL_INSTRUCTOR = "instructoremail";
    public static final String TAG_JSON_PHONE_INSTRUCTOR = "instructorphone";

    public static String INSTRUCTOR_ID = "instructor_id";

    //Subject
    public static final String URL_GET_ALL_SUBJECT = "http://192.168.30.26/project/subject/alldata.php";
    public static final String URL_GET_DETAIL_SUBJECT = "http://192.168.30.26/project/subject/detail.php?subject_id=";
    public static final String URL_ADD_SUBJECT = "http://192.168.30.26/project/subject/add.php";
    public static final String URL_UPDATE_SUBJECT = "http://192.168.30.26/project/subject/update.php?subject_id=";
    public static final String URL_DELETE_SUBJECT = "http://192.168.30.26/project/subject/delete.php?subject_id=";


    public static final String KEY_ID_SUBJECT = "subjectid";
    public static final String KEY_NAME_SUBJECT = "subjectname";

    public static final String TAG_JSON_ARRAY_SUBJECT = "subjectresult";
    public static final String TAG_JSON_ID_SUBJECT = "subjectid";
    public static final String TAG_JSON_NAME_SUBJECT = "subjectname";

    public static String SUBJECT_ID = "instructor_id";

    //Class
    public static final String URL_GET_ALL_CLASS = "http://192.168.30.26/project/class/alldata.php";
    public static final String URL_GET_DETAIL_CLASS = "http://192.168.30.26/project/class/detail.php?class_id=";
    public static final String URL_ADD_CLASS = "http://192.168.30.26/project/class/add.php";
    public static final String URL_UPDATE_CLASS = "http://192.168.30.26/project/class/update.php?id=";
    public static final String URL_DELETE_CLASS = "http://192.168.30.26/project/class/delete.php?class_id=";


    public static final String KEY_ID_CLASS = "classid";
    public static final String KEY_START_CLASS = "classstart";
    public static final String KEY_END_CLASS = "classend";
    public static final String KEY_INSTRUCTOR_CLASS = "classinstructor";
    public static final String KEY_SUBJECT_CLASS = "classsubject";

    public static final String TAG_JSON_ARRAY_CLASS = "classresult";
    public static final String TAG_JSON_ID_CLASS = "classid";
    public static final String TAG_JSON_START_CLASS = "classstart";
    public static final String TAG_JSON_END_CLASS = "classend";
    public static final String TAG_JSON_INSTRUCTOR_CLASS = "classinstructor";
    public static final String TAG_JSON_SUBJECT_CLASS = "classsubject";

    public static String CLASS_ID = "class_id";

    //Company
    public static final String URL_GET_ALL_COMPANY = "http://192.168.30.26/project/company/alldata.php";
    public static final String URL_GET_DETAIL_COMPANY = "http://192.168.30.26/project/company/detail.php?company_id=";
    public static final String URL_ADD_COMPANY = "http://192.168.30.26/project/company/add.php";
    public static final String URL_UPDATE_COMPANY = "http://192.168.30.26/project/company/update.php?company_id=";
    public static final String URL_DELETE_COMPANY = "http://192.168.30.26/project/company/delete.php?company_id=";


    public static final String KEY_ID_COMPANY = "companyid";
    public static final String KEY_NAME_COMPANY = "companyname";
    public static final String KEY_ADDRESS_COMPANY = "companyaddress";

    public static final String TAG_JSON_ARRAY_COMPANY = "companyresult";
    public static final String TAG_JSON_ID_COMPANY = "companyid";
    public static final String TAG_JSON_NAME_COMPANY = "companyname";
    public static final String TAG_JSON_ADDRESS_COMPANY = "companyaddress";

    public static String COMPANY_ID = "company_id";

    //Detail Class
    public static final String URL_GET_ALL_CLASSDETAIL = "http://192.168.30.26/project/classdetail/alldata.php";
    public static final String URL_GET_DETAIL_CLASSDETAIL = "http://192.168.30.26/project/classdetail/detail.php?company_id=";
    public static final String URL_ADD_CLASSDETAIL = "http://192.168.30.26/project/classdetail/add.php";
    public static final String URL_UPDATE_CLASSDETAIL = "http://192.168.30.26/project/classdetail/update.php?company_id=";
    public static final String URL_DELETE_CLASSDETAIL = "http://192.168.30.26/project/classdetail/delete.php?company_id=";


    public static final String KEY_ID_CLASSDETAIL = "classdetailid";
    public static final String KEY_CLASSID_CLASSDETAIL = "classdetailclassid";
    public static final String KEY_PARTICIPANTID_CLASSDETAIL = "classdetailparticipantid";

    public static final String TAG_JSON_ARRAY_CLASSDETAIL = "classdetailresult";

    public static final String TAG_JSON_ID_CLASSDETAIL = " ";
    public static final String TAG_JSON_CLASSID_CLASSDETAIL = "classdetailclassid";
    public static final String TAG_JSON_PARTICIPANTID_CLASSDETAIL = "classdetailparticipantid";

    public static final String TAG_JSON_SUBJECTNAME_CLASSDETAIL = "classdetailsubjectname";
    public static final String TAG_JSON_INSTRUCTORNAME_CLASSDETAIL = "classdetailinstructorname";
    public static final String TAG_JSON_TOTALPARTICIPANT_CLASSDETAIL = "classdetailtotalparticipant";

    public static String CLASSDETAIL_ID = "classresult";
}
