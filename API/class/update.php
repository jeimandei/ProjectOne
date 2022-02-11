<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id = $_POST['classid'];
		$start = $_POST['classstart'];
		$end = $_POST['classend'];
		$ins = $_POST['classinstructor'];
		$sub = $_POST['classsubject'];
		
		//import file koneksi database 
		require_once('../connection.php');
		
		//Membuat SQL Query
		$sql = "UPDATE class SET ClassStart = '$start', ClassEnd = '$end', InstructorID = CAST('$ins' AS INT), SubjectID = CAST('$sub' AS INT) WHERE ClassID = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Pegawai';
		}else{
			echo 'Gagal Update Data Pegawai';
		}
		
		mysqli_close($con);
	}
?>