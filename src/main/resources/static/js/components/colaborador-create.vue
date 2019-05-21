<template>	
    <q-page-container >
        <q-page padding>
            <span th:text="${teste}">Teste</span>
            <transition
            enter-active-class="animated bounceInLeft"
            leave-active-class="animated bounceOutRight"
            appear
            >
                <q-alert
                    v-if="resposta.exibir && !resposta.sucesso"
                    type="negative"
                    appear
                    class="q-mb-sm"
                >
                    <p v-for="erro in resposta.erros">{{ erro }}</p>
                </q-alert>

                <q-alert
                    v-if="resposta.exibir && resposta.sucesso"
                    type="positive"
                    appear
                    class="q-mb-sm"
                >
                    {{ resposta.mensagem }}
                </q-alert>
            </transition>
            <div class="row q-px-md">
                <div class="q-headline text-weight-light col-9 col-sm-8 col-xs-8">Novo Colaborador </div>
                
            </div>
            <div class="row">	            
                <div class="col-sm-8 col-md-8 col-xs-12 q-px-md">
                    <q-input  v-model="colaborador.nome" float-label="Nome"   />
                </div> 
                <div class="col-sm-4 col-md-4 col-xs-12 q-px-md">
                    <q-input  v-model="colaborador.cpfOuCnpj" float-label="CPF/CNPJ"   />
                </div> 
                <div class="col-sm-5 col-md-5 col-xs-12 q-px-md">
                    <q-input  v-model="colaborador.logradouro" float-label="Endereço"   />
                </div> 
                <div class="col-sm-2 col-md-2 col-xs-3 q-px-md">
                    <q-input  v-model="colaborador.numero" float-label="Número"   />
                </div> 
                <div class="col-sm-3 col-md-3 col-xs-5 q-px-md">
                    <q-input  v-model="colaborador.bairro" float-label="Bairro"   />
                </div> 
                <div class="col-sm-2 col-md-2 col-xs-4 q-px-md">
                    <q-input  v-model="colaborador.cep" float-label="CEP"   />
                </div> 
                 <div class="col-12 q-px-md">
                    <q-input  v-model="colaborador.complemento" float-label="Complemento"   />
                </div>
                <div class="col-sm-4 col-md-4 col-xs-12 q-px-md">
                    <q-input  v-model="colaborador.celular" float-label="Celular"   />
                </div>
                <div class="col-sm-4 col-md-4 col-xs-12 q-px-md">
                    <q-input  v-model="colaborador.email" float-label="E-mail"   />
                </div>
                <div class="col-sm-4 col-md-4 col-xs-12 q-px-md">
                    <q-input  v-model="colaborador.profissao" float-label="Profissão"   />
                </div>
                <div class="col-12 q-px-md">
                    <q-input  v-model="colaborador.comoColaborar" float-label="Como colabora?"   />
                </div>
            </div> <br>
            <div class="row q-px-md">
                <div class="col-md-2 col-sm-6 col-xs-6">
                    <a href="/colaboradores">
                        <q-btn
                            color="negative"
                            icon="fas fa-arrow-left"
                            label="Voltar"
                            class=""
                        />
                    </a>
                </div>
                <div class="col-md-2 col-sm-6 col-xs-6">
                        <q-btn
                            color="secondary"
                            icon="fas fa-save"
                            label="Salvar"
                            class=""
                            @click="create()"
                        />
                </div>
            </div>
            <!-- {{ colaborador }} -->
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
              cpfOuCnpj:"",
              comoColaborar:"",
        },
        resposta:{
            exibir:false,
            sucesso:false,
            mensagem:"",
            erros:[]
        }


      }
    }, 

    methods: {  
        findAll() {
            axios
            .get('/api/v1/colaboradores')
            .then(response => {this.tableData = response.data})
            .catch(error => console.log(error))
        },
        create() {
            this.resposta.exibir = false
            if(this.colaborador.cpfOuCnpj == ""){
                this.colaborador.cpfOuCnpj = null
            }
            console.log(this.colaborador)

            axios
                .post('/api/v1/colaboradores', this.colaborador)
                .then(response => {
                    this.resposta.sucesso = response.data.success
                    this.resposta.mensagem = response.data.message
                    this.resposta.exibir = true
                    console.log(response.data)
                })
                .catch(error => {
                    if(error.response.data.errors){
                        this.resposta.sucesso = false
                        this.resposta.erros = error.response.data.errors
                        this.resposta.exibir = true
                        console.log(this.resposta)
                    }

                    console.log(error.response.data)
                })
        }      
    },

    mounted() {
        this.findAll()
    }
  }
</script>