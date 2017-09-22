<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

class Cek extends CI_Model {

    public function get_data() {
        $query = $this->db->get('datatraining');
        return $query->result_array();
    }
}