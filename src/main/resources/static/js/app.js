var productService = {
    		  findAll(fn) {
    		    axios
    		      .get('/api/v1/colaboradores')
    		      .then(response => fn(response))
    		      .catch(error => console.log(error))
    		  },

    		  findById(id, fn) {
    		    axios
    		      .get('/api/v1/products/' + id)
    		      .then(response => fn(response))
    		      .catch(error => console.log(error))
    		  },

    		  create(coladorador, fn) {
    		    axios
    		      .post('/api/v1/products', product)
    		      .then(response => fn(response))
    		      .catch(error => console.log(error))
    		  },

    		  update(id, product, fn) {
    		    axios
    		      .put('/api/v1/products/' + id, product)
    		      .then(response => fn(response))
    		      .catch(error => console.log(error))
    		  },

    		  delete(id, fn) {
    		    axios
    		      .delete('/api/v1/products/' + id)
    		      .then(response => fn(response))
    		      .catch(error => console.log(error))
    		  }
    		}

      