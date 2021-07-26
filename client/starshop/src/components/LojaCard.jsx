import { Link } from "react-router-dom";

const handleUri = (uri) => {
  const hashtagIndex = uri.indexOf("#");
  return uri.substring(hashtagIndex + 1);
};

const LojaCard = (props) => {
  return (
    <div className="container loja-card-wrapper">
      <div className="row">
        <div className="col-12">{/* fotinha da loja */}</div>
      </div>
      <div className="row">
        <div className="col-12">
          {/* nome da Loja */}
          <Link
            to={`/loja?id=${handleUri(props.uri)}&nomeLoja=${props.nomeLoja}`}
          >
            {props.nome} {!props.isAtiv ? `- ${props.atividade}` : null}
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LojaCard;
