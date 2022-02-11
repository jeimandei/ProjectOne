<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$name = $_POST['instructorname'];
		$email = $_POST['instructoremail'];
		$phone = $_POST['instructorphone'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO instructor (InstructorName, InstructorEmail, InstructorPhone) VALUES ('$name','$email','$phone')";
		
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