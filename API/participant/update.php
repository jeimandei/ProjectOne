<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id = $_POST['participantid'];
		$name = $_POST['participantname'];
		$email = $_POST['participantemail'];
		$phone = $_POST['participantphone'];
		$company = $_POST['participantcompany'];
		
		//import file koneksi database 
		require_once('../connection.php');
		
		//Membuat SQL Query
		$sql = "UPDATE participant SET ParticipantName = '$name', ParticipantEmail = '$email', ParticipantPhone = '$phone', CompanyID = CAST('$company' AS INT) WHERE ParticipantID = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Pegawai';
		}else{
			echo 'Gagal Update Data Pegawai';
		}
		
		mysqli_close($con);
	}
?>