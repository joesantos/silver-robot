import { Link } from "react-router-dom";

export const sumProdutos = (arr) => {
  let soma = 0;
  arr.forEach((item) => {
    soma = soma + item.valor;
  });

  return soma;
};

const Carrinho = (props) => {
  return (
    <div className="carrinho-wrapper">
      <div className="row">
        <div className="col-12 d-flex justify-content-end mb-5">
          <button onClick={() => props.setMostrarCarrinho(false)}>
            Fechar carrinho
          </button>
        </div>
      </div>

      {props.produtos && props.produtos.length > 0 ? (
        props.produtos.map((produto, i) => {
          return (
            <>
              <div className="mb-3">
                {produto.nome} - R$ {produto.valor.toFixed(2)}
                <i
                  onClick={() => {
                    let novo = props.removeItem(produto);
                    props.setProdutosCarrinho(novo);
                  }}
                  className="fas fa-trash"
                  style={{ marginLeft: "10px", color: "#ff6666" }}
                ></i>
              </div>
            </>
          );
        })
      ) : (
        <div>Carrinho vazio</div>
      )}
      {props.produtos && props.produtos.length > 0 ? (
        <div className="text-center mt-4">
          <div className="mb-4">
            Total: R$ {sumProdutos(props.produtos).toFixed(2)}
          </div>
          <Link className="confirma-compra-button" to="/confirmaCompra">
            Confirmar compra
          </Link>
        </div>
      ) : null}
    </div>
  );
};

export default Carrinho;
