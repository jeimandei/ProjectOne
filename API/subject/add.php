<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$name = $_POST['subjectname'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO subject (SubjectName) VALUES ('$name')";
		
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