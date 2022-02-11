<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id = $_POST['instructorid'];
		$name = $_POST['instructorname'];
		$email = $_POST['instructoremail'];
		$phone = $_POST['instructorphone'];
		
		//import file koneksi database 
		require_once('../connection.php');
		
		//Membuat SQL Query
		$sql = "UPDATE instructor SET InstructorName = '$name', InstructorEmail = '$email', InstructorPhone = '$phone' WHERE InstructorID = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Pegawai';
		}else{
			echo 'Gagal Update Data Pegawai';
		}
		
		mysqli_close($con);
	}
?>