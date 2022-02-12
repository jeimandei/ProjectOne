<?php 
	//Import File Koneksi Database
	require_once('../connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT cd.ClassDetailID, c.ClassID, s.SubjectName, i.InstructorName, c.ClassStart, COUNT(cd.ParticipantID) AS 'TotalParticipant' FROM class_detail cd JOIN participant p ON cd.ParticipantID = p.ParticipantID JOIN class c ON cd.ClassID = c.ClassID JOIN subject s ON c.SubjectID = s.SubjectID JOIN instructor i on i.InstructorID=c.InstructorID GROUP BY s.SubjectName ORDER BY cd.ClassDetailID ASC";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"classdetailid"=>$row['ClassDetailID'],
			"classdetailsubjectname"=>$row['SubjectName'],
			"classdetailinstructorname"=>$row['InstructorName'],
			"classdetailtotalparticipant"=>$row['TotalParticipant'],
			"classdetailclassstart"=>$row['ClassStart'],
			"classdetailclassid"=>$row['ClassID']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('classdetailresults'=>$result));
	
	mysqli_close($con);
?>