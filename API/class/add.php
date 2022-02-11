<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$start = $_POST['classstart'];
		$end = $_POST['classend'];
		$ins = $_POST['classinstructor'];
		$sub = $_POST['classsubject'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO class (ClassStart, ClassEnd, InstructorID, SubjectID) VALUES ('$start','$end',CAST('$ins' AS int), CAST('$sub' AS int))";
		
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