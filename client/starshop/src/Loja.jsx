import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import Carrinho from "./components/Carrinho";

const getProdutos = async (idLoja) => {
  try {
    let response = await fetch(
      `http://localhost:3008/produtosPorLoja?id=${idLoja}`
    );
    let json = await response.json();

    return json;
  } catch (err) {
    console.error(err.message);
  }
};

const handleUri = (uri) => {
  const hashtagIndex = uri.indexOf("#");
  return uri.substring(hashtagIndex + 1);
};

const adicionaCarrinho = (produto) => {
  let atual = localStorage.getItem("carrinho");
  let quantidade = 0;

  if (atual) {
    let atualJson = JSON.parse(atual);

    let count = 0;
    atualJson.forEach((prod, index) => {
      if (prod.nome === produto.nomeProduto) {
        count = count + 1;
      }
    });

    if (count < produto.quantidade) {
      atualJson.forEach((item) => {
        if (item.nome === produto.nomeProduto) {
          quantidade = quantidade + 1;
        }
      });

      let novoProduto = {
        nome: produto.nomeProduto,
        uri: produto.uris[quantidade],
        valor: produto.valorProduto,
        tipoProduto: handleUri(produto.tipoProduto),
        idProduto: handleUri(produto.uris[quantidade]),
      };

      atualJson.push(novoProduto);
      localStorage.setItem("carrinho", JSON.stringify(atualJson));

      return atualJson;
    }

    return atualJson;
  } else {
    let atualJson = [];

    let novoProduto = {
      nome: produto.nomeProduto,
      uri: produto.uris[quantidade],
      valor: produto.valorProduto,
      tipoProduto: handleUri(produto.tipoProduto),
      idProduto: handleUri(produto.uris[quantidade]),
    };

    atualJson.push(novoProduto);
    localStorage.setItem("carrinho", JSON.stringify(atualJson));
    return atualJson;
  }
};

const getProdutosSemRepeticao = (array) => {
  let novoArray = [];

  array.forEach((produto) => {
    var quantidade = 0;
    var nomeProduto = produto.nome;

    var exists = novoArray.find(
      (produto) => produto.nomeProduto === nomeProduto
    );

    if (exists) {
    } else {
      array.forEach((prod) => {
        var isAdd = false;

        if (prod.nome === nomeProduto && quantidade === 0) {
          isAdd = true;

          novoArray.push({
            nomeProduto,
            quantidade: quantidade + 1,
            valorProduto: prod.valorProduto,
            tipoProduto: prod.tipoProduto,
            uris: [prod.uri],
          });
        }
        if (prod.nome === nomeProduto && quantidade !== 0) {
          var test = novoArray.find(
            (produto) => (produto.nomeProduto = nomeProduto)
          );

          var indexTest = novoArray.indexOf(test);

          novoArray[indexTest].quantidade = quantidade + 1;
          novoArray[indexTest].uris.push(prod.uri);

          isAdd = true;
        }

        if (isAdd) {
          quantidade = quantidade + 1;
        }
      });
    }
  });

  return novoArray;
};

const removerItemCarrinho = (produto) => {
  let atual = localStorage.getItem("carrinho");
  let atualJson = JSON.parse(atual);
  let produtoIndex;

  atualJson.forEach((prd, i) => {
    if (prd.nome === produto.nome) {
      produtoIndex = i;
    }
  });

  let novo = atualJson.filter((prd, i) => i != produtoIndex);

  localStorage.setItem("carrinho", JSON.stringify(novo));
  return novo;
};

const Loja = () => {
  const search = useLocation().search;
  const id = new URLSearchParams(search).get("id");
  const nomeLoja = new URLSearchParams(search).get("nomeLoja");

  useEffect(() => {
    async function fetchData() {
      const produtosResponse = await getProdutos(id);
      let produtosResponseNaoComprado = produtosResponse.filter(
        (produto) => !produto.ecomprado
      );
      setProdutos(produtosResponseNaoComprado);

      var psr = getProdutosSemRepeticao(produtosResponseNaoComprado);
      setProdutosSemRepeticao(psr);
    }

    fetchData();
  }, []);

  const [produtos, setProdutos] = useState([]);
  const [produtosCarrinho, setProdutosCarrinho] = useState(
    JSON.parse(localStorage.getItem("carrinho"))
  );
  const [produtosSemRepeticao, setProdutosSemRepeticao] = useState([]);
  const [mostrarCarrinho, setMostrarCarrinho] = useState(false);

  const [mostrarMapa, setMostrarMapa] = useState(false);

  return (
    <div className="container">
      <img
        style={
          mostrarMapa
            ? {
                position: "absolute",
                width: "600px",
                boxShadow: "0px 0px 20px #aaa",
                top: "200px",
              }
            : { display: "none" }
        }
        src={`/maps/${id}.jpeg`}
      />
      {mostrarCarrinho ? (
        <Carrinho
          style={mostrarCarrinho ? { display: "inherit" } : { display: "none" }}
          produtos={produtosCarrinho}
          removeItem={removerItemCarrinho}
          setMostrarCarrinho={setMostrarCarrinho}
          setProdutosCarrinho={setProdutosCarrinho}
        ></Carrinho>
      ) : null}
      <div className="row text-center my-5">
        <div className="col-8">
          <h2>{nomeLoja}</h2>
        </div>
        <div className="col-4">
          <div
            onClick={() => {
              setMostrarCarrinho(true);
            }}
          >
            <i
              style={
                produtosCarrinho && produtosCarrinho.length > 0
                  ? { color: "#3a6" }
                  : { color: "#ccc" }
              }
              className="fas fa-shopping-cart fa-2x"
            ></i>
          </div>
        </div>
      </div>

      <div className="row">
        <div className="col-6">
          {mostrarMapa ? (
            <button
              onClick={() => {
                setMostrarMapa(false);
              }}
            >
              Esconder Caminho
            </button>
          ) : (
            <button
              onClick={() => {
                setMostrarMapa(true);
              }}
            >
              Mostrar Caminho
            </button>
          )}
        </div>
      </div>
      <div className="row">
        <div className="col-12 py-4">
          {produtosSemRepeticao ? (
            produtosSemRepeticao.map((produto) => (
              <div className="produto-wrapper">
                {produto.nomeProduto} - {produto.valorProduto} - Qtd:{" "}
                {produto.quantidade}{" "}
                <button
                  style={{
                    marginLeft: "40px",
                    color: "#fff",
                    backgroundColor: "#3a6",
                  }}
                  onClick={() => {
                    let novoCarrinho = adicionaCarrinho(produto);
                    setProdutosCarrinho(novoCarrinho);
                  }}
                >
                  Adicionar ao carrinho
                </button>
              </div>
            ))
          ) : (
            <p>N??o h?? produtos</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Loja;
