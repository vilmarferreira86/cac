<template>
    <q-page-container>
        <q-page padding>
            <!-- <span th:text="${teste}">Teste</span> -->
           <div class="q-table-container q-table-dense">
                <div class="row justify-center">
                    <div class="text-secondary q-display-1">{{ titlePage }}</div>
                </div>
                <div class="q-table-middle scroll">
                    <table class="q-table q-table-horizontal-separator">
                        <thead>
                            <tr>
                                <th class="text-left sortable" style="width: 400px;">
                                    Nome<i aria-hidden="true" class="q-icon q-table-sort-icon q-table-sort-icon-left fas fa-arrow-up">
                                            </i>
                                </th>
                                <th class="text-left">Celular</th>
                                <th class="text-left sortable">E-mail<i aria-hidden="true" class="q-icon q-table-sort-icon q-table-sort-icon-left fas fa-arrow-up">
                                            </i></th>
                                <th class="text-right">CPF/CNPJ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="" v-for="item in dados.content">
                                <td class="text-left" style="width: 400px;">{{ item.nome }}</td>
                                <td class="text-left">{{ item.celular }}</td>
                                <td class="text-left">{{ item.email }}</td>
                                <td class="text-right">{{ item.cpfOuCnpj }}</td>
                            </tr>
                        </tbody>
                    </table>  
                </div>
    
                <div class="justify-center row">
                    <div center class="q-pagination row no-wrap items-center">
                        <button @click="launch(firstPageUrl)" :disabled="isDisabledFirst" tabindex="0" type="button" class="q-btn inline relative-position q-btn-item non-selectable q-btn-rectangle q-btn-flat q-focusable q-hoverable text-secondary">
                            <div class="q-focus-helper"></div>
                            <div class="q-btn-inner row col items-center q-popup--skip justify-center"><i aria-hidden="true" class="q-icon fas fa-step-backward">
                                            </i></div>
                        </button>
                        <button @click="launch(previousPageUrl)" :disabled="isDisabledFirst" tabindex="0" type="button" class="q-btn inline relative-position q-btn-item non-selectable q-btn-rectangle q-btn-flat q-focusable q-hoverable text-secondary">
                            <div class="q-focus-helper"></div>
                            <div class="q-btn-inner row col items-center q-popup--skip justify-center"><i aria-hidden="true" class="q-icon fas fa-chevron-left">
                                            </i></div>
                        </button>
                        <div class="row justify-center">
                            <div tabindex="-1" class="q-if row no-wrap relative-position q-input inline no-padding q-if-hide-underline q-if-standard q-if-has-content text-secondary" style="width: 13rem;">
                                <div class="q-if-baseline">|</div>
                                <div class="q-if-inner col column q-popup--skip">
                                    <div class="row no-wrap relative-position">
                                        <input disabled type="text" :placeholder="page" step="any" class="col q-input-target q-no-input-spinner ellipsis">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button @click="launch(nextPageUrl)" :disabled="isDisabledLast" tabindex="0" type="button" class="q-btn inline relative-position q-btn-item non-selectable q-btn-rectangle q-btn-flat q-focusable q-hoverable text-secondary">
                            <div class="q-focus-helper"></div>
                            <div class="q-btn-inner row col items-center q-popup--skip justify-center">
                                <i aria-hidden="true" class="q-icon fas fa-chevron-right">
                                            </i>
                            </div>
                        </button>
                        <button @click="launch(lastPageUrl)" :disabled="isDisabledLast"  tabindex="0" type="button" class="q-btn inline relative-position q-btn-item non-selectable q-btn-rectangle q-btn-flat q-focusable q-hoverable text-secondary">
                            <div class="q-focus-helper"></div>
                            <div class="q-btn-inner row col items-center q-popup--skip justify-center">
                                <i aria-hidden="true" class="q-icon fas fa-step-forward">
                                            </i>
                            </div>
                        </button>
                    </div>
                </div>
            </div>
			<ul>
				<li v-for="(item, key, index) in objectItems" v-if="permitidos.includes(key)">
				{{ item }} - {{ key }} - {{ index }}</li>
			</ul>
		</q-page>
    </q-page-container>
</template>

<script>
    module.exports = {
        
        props: ['dados', 'url'],
        data() {
            return {
                titlePage: "Colaboradores",
                page:0,
                first:true,
                last:false,
                objectItems: {
                    key1: 'item1',
                    key2: 'item 2',
                    key3: 'item3'
                },
                permitidos:[
                	'key1','key2'
                ]
            }
        },
    
        methods: {
             launch: function (url) {
                 window.location.href = url;
             },
            goToFirst: function(){
                console.log("chamou");
                this.launch(this.url+"?page="+0)
            },
            goToLast: function(){
                console.log("chamou");
                this.launch(this.url+"?page="+(dados.totalPages-1))
            }
    
        },
        mounted() {
            this.page=(dados.pageable.pageNumber + 1)+" / "+dados.totalPages,
            this.first = this.dados.first,
            this.last = this.dados.last
        },
        computed:{
            isDisabledLast: function(){
                return this.last;
            },
            isDisabledFirst: function(){
                return this.first;
            },
            lastPageUrl: function () {
                return this.url+"?page="+(this.dados.totalPages-1);
            },
            firstPageUrl: function () {
                return this.url+"?page="+0;
            },
            previousPageUrl: function () {
                if(dados.number == 0){
                    return this.url+"?page=0";
                }
                return this.url+"?page="+(this.dados.number-1);
            },
            nextPageUrl: function () {
                return this.url+"?page="+(this.dados.number+1);
            }
        }
    }
</script>