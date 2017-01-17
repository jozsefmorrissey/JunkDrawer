using System;
using System.Windows.Forms;
using System.Drawing;
using System.Threading;
using System.Drawing.Drawing2D;
using System.Threading.Tasks;

namespace ContraCloneImproved
{
	public class Window : Form
	{
		private Level level;
		private Bitmap buffer;
		private Bitmap oldBuf;
		System.Windows.Forms.Timer controlTimer;


		public Window (Level lvl) : base()
		{
			this.level = lvl;
			
       		this.KeyPreview = true;
			controlTimer = new System.Windows.Forms.Timer ();


			buffer = new Bitmap (this.Width, this.Height);
			Build ();
		}

		public void addControl(int refreshRate, Control control)
		{
			controlTimer.Interval = refreshRate;
			controlTimer.Tick += control.timerCallback;
			controlTimer.Start (); 
		}

		public void removeControl (Control control)
		{
			controlTimer.Tick -= control.timerCallback;
		}

		public void Build()
		{
			this.WindowState = FormWindowState.Maximized;

			this.Text = "Contra Clone 5000";

			this.ResizeRedraw = true;
			this.HelpButton = true;
			this.MaximizeBox = true;
			this.MinimizeBox = true;
		}

		// Found this function on stack overflow http://stackoverflow.com/questions/10714358/draw-on-a-form-by-a-separate-thread
		public void Draw()
		{

			buffer = new Bitmap(this.Width, this.Height);
			//start an async task
			Task.Factory.StartNew( () =>
				{
					using (Graphics g = Graphics.FromImage(buffer))
					{
						level.paint (g);
					}
					//invoke an action against the main thread to draw the buffer to the background image of the main form.
					this.Invoke( new Action(() =>
						{
							this.BackgroundImage = buffer;
						}));
				});

		}

		protected override void OnPaint(PaintEventArgs e)
		{

			Graphics formGraphics;
			formGraphics = this.CreateGraphics ();

			e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;

			base.OnPaint (e);

			formGraphics.Dispose ();

			if (oldBuf != null)
				oldBuf.Dispose ();
			oldBuf = buffer;
		}

		protected override void OnResize (EventArgs e)
		{
			level.resize ();
		}

		public Dimentions getDimentions(){return new Dimentions (this.Width, this.Height);}

		public void setLevel (Level lvl) { level = lvl;}
	}
}

