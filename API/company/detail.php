<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['company_id'];
	
	//Importing database
	require_once('../connection.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM company WHERE CompanyID=$id";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
		"companyid"=>$row['CompanyID'],
		"companyname"=>$row['CompanyName'],
		"companyaddress"=>$row['CompanyAddress']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('companyresult'=>$result));
	
	mysqli_close($con);
?>