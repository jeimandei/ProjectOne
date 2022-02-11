<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$name = $_POST['companyname'];
		$address = $_POST['companyaddress'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO company (CompanyName,CompanyAddress) VALUES ('$name','$address')";
		
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