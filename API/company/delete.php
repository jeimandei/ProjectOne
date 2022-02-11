<?php 
 //Mendapatkan Nilai ID
 $id = $_GET['company_id'];
 
 //Import File Koneksi Database
 require_once('../connection.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM company WHERE CompanyID=$id;";

 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Pegawai';
 }else{
 echo 'Gagal Menghapus Pegawai';
 }
 
 mysqli_close($con);
 ?>