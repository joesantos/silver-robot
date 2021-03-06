import { useState, useEffect } from "react";
import { sumProdutos } from "./components/Carrinho";

const postConfirmarCompra = async (produtos, usuario) => {
  let produtosPayload = [];

  produtos.forEach((produto) => {
    produtosPayload.push({
      usuario,
      compraTipoProduto: `compra${produto.tipoProduto}`,
      idProduto: produto.idProduto,
      tipoProduto: produto.tipoProduto,
    });
  });

  produtosPayload.forEach(async (item) => {
    await fetch("http://localhost:3008/confirmaCompra", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(item),
    });
  });

  localStorage.removeItem("carrinho");

  window.location.href = "/";
};

const ConfirmaCompra = (props) => {
  const [produtosCarrinho, setProdutosCarrinho] = useState(
    JSON.parse(localStorage.getItem("carrinho"))
  );

  const [usuario, setUsuario] = useState("joelson");

  return (
    <div className="container text-center">
      <div className="row my-5">
        <div className="col-12">
          <label htmlFor="">Usuário: </label>
          <select
            value={usuario}
            onChange={(event) => setUsuario(event.target.value)}
          >
            <option value="joelson">Joelson</option>
            <option value="pablo">Pablo</option>
          </select>
        </div>
      </div>
      <div className="row">
        <div className="col-12">
          {produtosCarrinho && produtosCarrinho.length > 0 ? (
            produtosCarrinho.map((produto, i) => {
              return (
                <>
                  <div>
                    {produto.nome} - {produto.valor}
                  </div>
                </>
              );
            })
          ) : (
            <div>Carrinho vazio</div>
          )}
          {produtosCarrinho && produtosCarrinho.length > 0 ? (
            <div>
              <div className="mb-4">
                Total: R$ {sumProdutos(produtosCarrinho).toFixed(2)}
              </div>
              <button
                className="confirma-compra-button"
                onClick={() => {
                  postConfirmarCompra(produtosCarrinho, usuario);
                }}
              >
                Finalizar compra
              </button>
            </div>
          ) : null}
        </div>
      </div>
    </div>
  );
};

export default ConfirmaCompra;
