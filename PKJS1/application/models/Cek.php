<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Cek extends CI_Model {

    public function get_data() {
        $query = $this->db->get('datatraining');
        return $query->result_array();
    }

    public function get_knn($x,$y,$z,$k)
    {
    	$query_statement = "select * from datatraining order by SQRT(POW(sumbuX-".$x.",2)+POW(sumbuY-".$y.",2)+POW(sumbuZ-".$z.",2)) limit ".$k;
    	$query = $this->db->query($query_statement);
    	return $query->result_array();
    }
}
