<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['class_id'];

	//Import File Koneksi Database
	require_once('../connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM class_detail cd JOIN participant p ON cd.ParticipantID = p.ParticipantID JOIN class c ON cd.ClassID = c.ClassID JOIN subject s ON c.SubjectID = s.SubjectID JOIN instructor i on i.InstructorID=c.InstructorID WHERE c.ClassID=$id;";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"classdetailid"=>$row['ClassDetailID'],
			"classdetailclassid"=>$row['ClassID'],
			"classdetailparticipantid"=>$row['ParticipantID'],
			"classdetailparticipantname"=>$row['ParticipantName'],
			"classdetailsubjectname"=>$row['SubjectName'],
			"classdetailinstructorname"=>$row['InstructorName']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('classdetailsresult'=>$result));
	
	mysqli_close($con);
?>