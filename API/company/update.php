<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id = $_POST['companyid'];
		$name = $_POST['companyname'];
		$address = $_POST['companyaddress'];
		
		//import file koneksi database 
		require_once('../connection.php');
		
		//Membuat SQL Query
		$sql = "UPDATE company SET CompanyName = '$name', CompanyAddress = '$address' WHERE CompanyID = $id;";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Pegawai';
		}else{
			echo 'Gagal Update Data Pegawai';
		}
		
		mysqli_close($con);
	}
?>