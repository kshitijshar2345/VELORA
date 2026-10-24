
function Checkout() {
  const { cart, user, setCart } = useStore();
  const nav = useNavigate();

  const [form, setForm] = useState({
    name: user?.name || '',
    email: user?.email || '',
    phone: '',
    address: '',
    city: '',
    pincode: ''
  });

  const [busy, setBusy] = useState(false);
  const [err, setErr] = useState('');

  if (!cart.length) {
    return (
      <div className="page-wrap">
        <Empty
          title="Your bag is empty."
          copy="Add something you love before checking out."
          action="Shop the collection"
          to="/shop"
        />
      </div>
    );
  }

  const subtotal = cart.reduce(
    (sum, p) => sum + p.pricePaise * p.quantity,
    0
  );

  const shipping = subtotal >= 500000 ? 0 : 49900;

  const field = (key, label, wide = false) => (
    <label className={'field ' + (wide ? 'wide' : '')} key={key}>
      {label}
      <input
        required
        value={form[key]}
        onChange={e =>
          setForm({ ...form, [key]: e.target.value })
        }
      />
    </label>
  );

  const submit = async e => {
    e.preventDefault();

    if (!user) {
      nav('/login?next=/checkout');
      return;
    }

    setBusy(true);
    setErr('');

    try {
      const shippingAddress =
        `${form.name}, ${form.address}, ${form.city}, ` +
        `${form.pincode}, phone ${form.phone}; email ${form.email}`;

      const order = await api('/orders', {
        method: 'POST',
        body: JSON.stringify({
          items: cart.map(p => ({
            productId: p.id,
            size: p.size,
            quantity: p.quantity
          })),
          shippingAddress,
          paymentMethod: 'COD'
        })
      });

      setCart([]);
      nav('/orders?success=' + order.id);
    } catch (error) {
      setErr(
        error.message ||
        'Order place nahi ho paya. Please try again.'
      );
    } finally {
      setBusy(false);
    }
  };

  return (
    <main className="page-wrap container">
      <div className="page-title">
        <span className="eyebrow">ALMOST YOURS</span>
        <h1>Checkout <em>with care.</em></h1>
      </div>

      <form className="checkout-layout" onSubmit={submit}>
        <div className="checkout-form">
          <h3>01 — Contact & delivery</h3>

          <div className="form-grid">
            {field('name', 'Full name')}
            {field('email', 'Email address')}
            {field('phone', 'Phone number')}
            {field('pincode', 'PIN code')}
            {field('address', 'Street address', true)}
            {field('city', 'City', true)}
          </div>

          <div className="payment-note">
            <ShieldCheck />
            <div>
              <b>Cash on Delivery</b>
              <p>
                Pay when your order arrives at your doorstep.
                No online payment required.
              </p>
            </div>
          </div>

          {err && <div className="error-line">{err}</div>}

          <button
            type="submit"
            disabled={busy}
            className="button button-light full"
          >
            {busy
              ? 'Placing your order…'
              : 'Place order — Cash on Delivery'}
            <ArrowRight size={16} />
          </button>
        </div>

        <aside className="summary">
          <span className="eyebrow">
            YOUR BAG · {cart.length} STYLE(S)
          </span>

          {cart.map(p => (
            <div className="mini-order" key={p.id + p.size}>
              <img src={p.imageUrl} alt={p.name} />
              <div>
                <b>{p.name}</b>
                <span>Size {p.size} · Qty {p.quantity}</span>
              </div>
              <strong>{money(p.pricePaise * p.quantity)}</strong>
            </div>
          ))}

          <div className="summary-row">
            <span>Subtotal</span>
            <b>{money(subtotal)}</b>
          </div>

          <div className="summary-row">
            <span>Shipping</span>
            <span>
              {shipping === 0 ? 'Complimentary' : money(shipping)}
            </span>
          </div>

          <div className="summary-row total">
            <span>Total</span>
            <b>{money(subtotal + shipping)}</b>
          </div>
        </aside>
      </form>
    </main>
  );
}
export default App;