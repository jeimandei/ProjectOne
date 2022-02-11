<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['participant_id'];
	
	//Importing database
	require_once('../connection.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM participant p JOIN company c ON p.CompanyID = c.CompanyID WHERE ParticipantID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
		"participantid"=>$row['ParticipantID'],
		"participantname"=>$row['ParticipantName'],
		"participantemail"=>$row['ParticipantEmail'],
		"participantphone"=>$row['ParticipantPhone'],
		"participantcompanyid"=>$row['CompanyID'],
		"participantcompany"=>$row['CompanyName']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('participantresult'=>$result));
	
	mysqli_close($con);
?>