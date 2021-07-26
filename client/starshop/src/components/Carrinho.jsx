import { Link } from "react-router-dom";

const Carrinho = (props) => {
  return (
    <div className="carrinho-wrapper">
      <button onClick={() => props.setMostrarCarrinho(false)}>
        Fechar carrinho
      </button>
      {props.produtos && props.produtos.length > 0 ? (
        props.produtos.map((produto, i) => {
          return (
            <>
              <div>
                {produto.nome} - {produto.valor}
                <button
                  onClick={() => {
                    let novo = props.removeItem(produto);
                    props.setProdutosCarrinho(novo);
                  }}
                >
                  Excluir
                </button>
              </div>
            </>
          );
        })
      ) : (
        <div>Carrinho vazio</div>
      )}
      {props.produtos && props.produtos.length > 0 ? (
        <div>
          <Link to="/confirmaCompra">Confirma compra</Link>
        </div>
      ) : null}
    </div>
  );
};

export default Carrinho;
