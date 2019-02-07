// carregar função ao abrir página
document.addEventListener("DOMContentLoaded", function (event) {
    listarPessoas();
});

// URL REST
var url = 'http://localhost:8080/crud-pessoa/rest/pessoa/';

// função para exibir mensagens
function toast(mensagem) {
    var toast = document.getElementById("toast");
    var texto = document.createTextNode(mensagem);
    toast.appendChild(texto);
    toast.className = "show";
    setTimeout(function () { toast.className = toast.className.replace("show", ""); toast.removeChild(texto); }, 3000);
}

// função para listar todas as pessoas na tabela
function listarPessoas() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            var data = JSON.parse(xhr.responseText);
            popularTabela(data);
        }
    }
    xhr.send();
}

// função para popular tabela com dados das pessoas
function popularTabela(data) {
    var tabela = document.getElementById('tabela');
    data.forEach(function (object) {
        var tr = document.createElement('tr');
        tr.innerHTML = '<tbody>' +
            '<td id="id">' + object.id + '</td>' +
            '<td>' + object.nome + '</td>' +
            '<td>' + object.endereco + '</td>' +
            '<td>' + object.email + '</td>' +
            '<td>' + '<button type="button" class="btn btn-dark espaco" onclick="abrirEditar(' + object.id + "," + "'" + object.nome + "'" + "," + "'" + object.endereco + "'" + "," + "'" + object.email + "'" + '); ">Editar</button>' +
            '<button type="button" class="btn btn-danger" onclick="excluirPessoa(' + object.id + ');">Excluir</button>' + '</td>' +
            '</tbody>';
        tabela.appendChild(tr);
    });
}

// Função para atualizar tabela ao final de alguma operação
function atualizarTabela() {
    // Apaga dados existentes na tabela
    var tabela = document.getElementById('tabela');
    var tr = tabela.getElementsByTagName('tr');
    var trCount = tr.length;
    for (var x = trCount - 1; x > 0; x--) {
        tabela.removeChild(tr[x]);
    }
    // lista dados atualizados
    listarPessoas();
}

// função para inserir pessoas
function inserirPessoa() {
    var pessoa = {};
    pessoa.nome = document.getElementById("nome").value;
    pessoa.endereco = document.getElementById("endereco").value;
    pessoa.email = document.getElementById("email").value;

    var xhr = new XMLHttpRequest();

    if (pessoa.nome != "" && pessoa.endereco != "" && pessoa.email != "") {
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-type', 'application/json');
        xhr.onreadystatechange = function () {
            if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
                $('#modalInserir').modal('hide');
                atualizarTabela();
                toast("Cadastro inserido com sucesso.");
                document.getElementById("nome").value = "";
                document.getElementById("endereco").value = "";
                document.getElementById("email").value = "";
            }
        }
        xhr.send(JSON.stringify(pessoa));
    } else {
        toast("Confira os dados preenchidos.");
    }
}

// função para abrir modal de editar pessoa
function abrirEditar(id, nome, endereco, email) {
    $('#modalEditar').modal('show');
    var textoId = document.getElementById("editarId");

    if (textoId.innerHTML == "") {
        textoId.innerHTML = id;
    } else {
        textoId.innerHTML = id;
    }

    document.getElementById("editarNome").value = nome;
    document.getElementById("editarEndereco").value = endereco;
    document.getElementById("editarEmail").value = email;
}

// função para editar pessoa
function editarPessoa() {
    var pessoa = {};
    pessoa.id = document.getElementById("editarId").innerHTML;
    pessoa.nome = document.getElementById("editarNome").value;
    pessoa.endereco = document.getElementById("editarEndereco").value;
    pessoa.email = document.getElementById("editarEmail").value;

    var xhr = new XMLHttpRequest();
    xhr.open('PUT', url + pessoa.id, true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            atualizarTabela();
            toast("Edição concluída.");
            $('#modalEditar').modal('hide');
        }
    }
    xhr.send(JSON.stringify(pessoa));
}

// função para exluir pessoa
function excluirPessoa(id) {
    var xhr = new XMLHttpRequest();
    xhr.open('DELETE', url + id, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
            atualizarTabela();
            toast("Pessoa deletada com sucesso.");
        }
    }
    xhr.send();
}

// função de pesquisa
function procurarPessoa() {
    var input, filter, found, table, tr, td, i, j;
    input = document.getElementById("pesquisar");
    filter = input.value.toUpperCase();
    table = document.getElementById("tabela");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td");
        for (j = 0; j < td.length; j++) {
            if (td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
                found = true;
            }
        }
        if (found) {
            tr[i].style.display = "";
            found = false;
        } else if (tr[i].id != 'cabecalhoTabela') {
            tr[i].style.display = "none";
        }
    }
}