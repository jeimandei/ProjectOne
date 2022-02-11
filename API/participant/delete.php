<?php 
 //Mendapatkan Nilai ID
 $id = $_GET['participant_id'];
 
 //Import File Koneksi Database
 require_once('../connection.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM participant WHERE ParticipantID=$id;";

 
 //Menghapus Nilai pada Database 
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Pegawai';
 }else{
 echo 'Gagal Menghapus Pegawai';
 }
 
 mysqli_close($con);
 ?>