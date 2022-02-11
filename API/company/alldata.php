<?php 
	//Import File Koneksi Database
	require_once('../connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM company";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"companyid"=>$row['CompanyID'],
			"companyname"=>$row['CompanyName'],
			"companyaddress"=>$row['CompanyAddress']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('companyresult'=>$result));
	
	mysqli_close($con);
?>