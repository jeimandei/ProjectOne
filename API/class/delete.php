<?php 
 //Mendapatkan Nilai ID
 $id = $_GET['class_id'];
 
 //Import File Koneksi Database
 require_once('../connection.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM class WHERE ClassID=$id;";

 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Pegawai';
 }else{
 echo 'Gagal Menghapus Pegawai';
 }
 
 mysqli_close($con);
 ?>