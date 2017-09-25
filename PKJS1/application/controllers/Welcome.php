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

	public function cek_array(){
		$xyz = $_GET["xyz"];
		foreach ($xyz as $key => $value) {
			# code...
			$data = explode("|",$value);
			foreach ($data as $key => $value2) {
				print_r($value2);
				echo " ";
			}
			echo "<br>";
		}
	}

}
