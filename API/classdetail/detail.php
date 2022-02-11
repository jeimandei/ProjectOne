<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['classdetail_id'];
	
	//Importing database
	require_once('../connection.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM class_detail cd JOIN participant p ON cd.ParticipantID = p.ParticipantID JOIN class c ON cd.ClassID = c.ClassID JOIN subject s ON c.SubjectID = s.SubjectID JOIN instructor i on i.InstructorID=c.InstructorID WHERE cd.ClassDetailID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
		"classdetailid"=>$row['ClassDetailID'],
		"classdetailparticipantid"=>$row['ParticipantID'],
		"classdetailparticipantname"=>$row['ParticipantName'],
		"classdetailclassid"=>$row['SubjectID'],
		"classdetailsubjectname"=>$row['SubjectName']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('classdetailresult'=>$result));
	
	mysqli_close($con);
?>