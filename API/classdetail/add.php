<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$part = $_POST['classdetailparticipantid'];
		$sub = $_POST['classdetailclassid'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO class_detail (ClassID, ParticipantID) VALUES (CAST('$sub' AS int), CAST('$part' AS int))";
		
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