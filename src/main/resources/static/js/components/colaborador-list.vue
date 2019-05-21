<template>	
    <q-page-container >
        <q-page padding>	            
            <q-table  
            title="Colaboradores" 
            rows-per-page-label="Registros por página"  
            :data="tableData"  
            :columns="columns"  
            row-key="name" 
            color="secondary"
            :filter="filter" 
            :separator="separator" 
            :visible-columns="visibleColumns" 
            no-data-label="Não há dados para exibir!" > 
            
                <template slot="top-left" slot-scope="props"> 
                    <div class="q-display-1">Colaboradores</div> 
                </template> 
                <template slot="top-right" slot-scope="props"> 
                    <table> 
                        <tr> 
                            <td> 
                                <q-search  placeholder="Busca..."  
                                hide-underline class="col-12"   
                                color="secondary" v-model="filter" /> 
                            </td> 
                            <td> 
                                <a href="/colaboradores/novo">
                                    <q-btn icon="fas fa-plus" color="primary" class="col-12" style="text-align:center" label="novo"/> 
                                </a>
                            </td> 
                        </tr> 
                    </table> 
                    
                </template>		 
            </q-table> 
        </q-page>	
    </q-page-container>

</template>

<script>
  module.exports = { 
      
    data() {
      return {
          columns: [
                  {
                    name: 'nome',
                    label: 'Colaborador',
                    field: 'nome',
                    required: true,
                    align: 'left',
                    sortable: true,
                    style: 'width: 400px'
                  },
                  { name: 'celular', label: 'Celular', field: 'celular', sortable: false, align: 'left' },
                  { name: 'email', label: 'E-mail', field: 'email', sortable: true, align: 'left' },
                  { name: 'cpfOuCnpj', label: 'CPF/CNPJ', field: 'cpfOuCnpj' }
                ],
            tableData: [],
            tableData2: [],
            filter: '',
            separator: 'horizontal',
            visibleColumns: ['nome', 'celular', 'email', 'cpfOuCnpj']
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