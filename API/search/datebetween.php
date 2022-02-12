<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$start = $_GET['start'];
    $end = $_GET['end'];
	
	//Importing database
	require_once('../connection.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM class c JOIN instructor i ON c.InstructorID = i.InstructorID JOIN subject s ON c.SubjectID = s.SubjectID WHERE c.ClassStart BETWEEN DATE('$start') AND DATE('$end') ORDER BY ClassStart";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();

    while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
		"classid"=>$row['ClassID'],
		"classstartdate"=>$row['ClassStart'],
		"classenddate"=>$row['ClassEnd'],
		"classinstructordate"=>$row['InstructorName'],
		"classinstructorid"=>$row['InstructorID'],
		"classsubjectdate"=>$row['SubjectName'],
		"classsubjectid"=>$row['SubjectID']
		));
	}

	//Menampilkan dalam format JSON
	echo json_encode(array('classdate'=>$result));
	
	mysqli_close($con);
?>