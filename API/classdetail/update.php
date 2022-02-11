<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id = $_POST['classdetailid'];
		$class = $_POST['classdetailclassid'];
		$participant = $_POST['classdetailparticipantid'];
		
		//import file koneksi database 
		require_once('../connection.php');
		
		//Membuat SQL Query
		$sql = "UPDATE class_detail SET ClassID = '$class', ParticipantID = '$participant' WHERE ClassDetailID = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Pegawai';
		}else{
			echo 'Gagal Update Data Pegawai';
		}
		
		mysqli_close($con);
	}
?>