<?php 
	//Import File Koneksi Database
	require_once('../connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM participant";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"participantid"=>$row['ParticipantID'],
			"participantname"=>$row['ParticipantName'],
			"participantemail"=>$row['ParticipantEmail'],
			"participantphone"=>$row['ParticipantPhone'],
			"participantcompany"=>$row['CompanyID']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('participantresult'=>$result));
	
	mysqli_close($con);
?>