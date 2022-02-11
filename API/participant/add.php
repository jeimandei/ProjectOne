<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$name = $_POST['participantname'];
		$email = $_POST['participantemail'];
		$phone = $_POST['participantphone'];
		$company = $_POST['participantcompany'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO participant (ParticipantName, ParticipantEmail, ParticipantPhone, CompanyID) VALUES ('$name','$email','$phone', CAST('$company' AS int))"; 
		
		//Import File Koneksi database
		require_once('../connection.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Pegawai';
		}else{
			echo 'Gagal Menambahkan Pegawai';
		}
		
		mysqli_close($con);
	}
?>