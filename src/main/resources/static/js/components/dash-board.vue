<template>	
    <q-page-container >
        <q-page padding>
                <h1>Dashboard</h1>
        </q-page>	
    </q-page-container>

</template>

<script>
  module.exports = { 
      
    data() {
      return {
          colaborador:{
              nome:"",
              logradouro:"",
              numero:"",
              bairro:"",
              cep:"",
              complemento:"",
              celular:"",
              email:"",
              profissao:"",
              cpfOuCnpj:""
            }

      }
    }, 

    methods: {  
        findAll() {
            axios
            .get('/api/v1/colaboradores')
            .then(response => {this.tableData = response.data})
            .catch(error => console.log(error))
        }      
    },

    mounted() {
        this.findAll()
    }
  }
</script>