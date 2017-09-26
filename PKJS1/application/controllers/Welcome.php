<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */
	public function index()
	{
		$data['sumbu'] = $this->Cek->get_data();
		$this->load->view('welcome_message', $data);
	}

	public function tambah_data($x, $y, $z, $class){
		$data = array(
		 	'sumbuX' => $x,
			'sumbuY' => $y,
		 	'sumbuZ' => $z,
		 	'class' => $class
		 	);
		$this->db->insert('datatraining', $data);
	}

	public function cek_duduk(){
		$xyz = $_GET["temp"];
		$total = sizeof($xyz);
		$X = 0;
		$Y = 0;
		$Z = 0;
		foreach ($xyz as $key => $value) {
			# code...
			$data = explode("|",$value);
			$X += $data[0];
			$Y += $data[1];
			$Z += $data[2];
		}
		$rataX = $X/10;
		$rataY = $Y/10;
		$rataZ = $Z/10;

		//k=1;
		$terkecil = 1000;
		$temp_class = 0;

		$query = $this->db->get('datatraining');
		foreach ($query->result() as $row)
		{
			$dX = $rataX - ($row->sumbuX);
			$dY = $rataY - ($row->sumbuY);
			$dZ = $rataZ - ($row->sumbuZ);
		    
		    //Pengkuadratan(KNN)
			$euc = sqrt((pow($dX,2)) + (pow($dY,2)) + (pow($dZ,2)));

			if ($euc < $terkecil) {
				$terkecil = $euc;
				$temp_class = $row->class;
			}
		}
		//$date = new DateTime();
		date_default_timezone_set("America/New_York"); 

		$testing = array(
			'time' => " ".date("h:i:sa"),
			'class_sebenarnya' => 1,
			'prediksi' => $temp_class
		);
		$this->db->insert('record_testing', $testing);
	}

	public function cek_naikmontor(){
		$xyz = $_GET["temp"];
		$total = sizeof($xyz);
		$X = 0;
		$Y = 0;
		$Z = 0;
		foreach ($xyz as $key => $value) {
			# code...
			$data = explode("|",$value);
			$X += $data[0];
			$Y += $data[1];
			$Z += $data[2];
		}
		$rataX = $X/10;
		$rataY = $Y/10;
		$rataZ = $Z/10;

		//k=1;
		$terkecil = 1000;
		$temp_class = 0;

		$query = $this->db->get('datatraining');
		foreach ($query->result() as $row)
		{
			$dX = $rataX - ($row->sumbuX);
			$dY = $rataY - ($row->sumbuY);
			$dZ = $rataZ - ($row->sumbuZ);
		    
		    //Pengkuadratan(KNN)
			$euc = sqrt((pow($dX,2)) + (pow($dY,2)) + (pow($dZ,2)));

			if ($euc < $terkecil) {
				$terkecil = $euc;
				$temp_class = $row->class;
			}
		}
		//$date = new DateTime();
		date_default_timezone_set("America/New_York"); 

		$testing = array(
			'time' => " ".date("h:i:sa"),
			'class_sebenarnya' => 2,
			'prediksi' => $temp_class
		);
		$this->db->insert('record_testing', $testing);
	}

	public function cek_naikmobil(){
		$xyz = $_GET["temp"];
		$total = sizeof($xyz);
		$X = 0;
		$Y = 0;
		$Z = 0;
		foreach ($xyz as $key => $value) {
			# code...
			$data = explode("|",$value);
			$X += $data[0];
			$Y += $data[1];
			$Z += $data[2];
		}
		$rataX = $X/10;
		$rataY = $Y/10;
		$rataZ = $Z/10;

		//k=1;
		$terkecil = 1000;
		$temp_class = 0;

		$query = $this->db->get('datatraining');
		foreach ($query->result() as $row)
		{
			$dX = $rataX - ($row->sumbuX);
			$dY = $rataY - ($row->sumbuY);
			$dZ = $rataZ - ($row->sumbuZ);
		    
		    //Pengkuadratan(KNN)
			$euc = sqrt((pow($dX,2)) + (pow($dY,2)) + (pow($dZ,2)));

			if ($euc < $terkecil) {
				$terkecil = $euc;
				$temp_class = $row->class;
			}
		}
		//$date = new DateTime();
		date_default_timezone_set("America/New_York"); 

		$testing = array(
			'time' => " ".date("h:i:sa"),
			'class_sebenarnya' => 3,
			'prediksi' => $temp_class
		);
		$this->db->insert('record_testing', $testing);
	}

}
